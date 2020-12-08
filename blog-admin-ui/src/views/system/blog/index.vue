<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--类型数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="typeName"
            placeholder="请输入类型名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="typeOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--博客数据-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="博客信息" prop="blogName">
            <el-input
              v-model="queryParams.searchValue"
              placeholder="请输入博客信息"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="状态" prop="published">
            <el-select
              v-model="queryParams.published"
              placeholder="博客状态"
              clearable
              size="small"
              style="width: 240px"
            >
              <el-option
                v-for="dict in publishedOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:blog:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:blog:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:blog:remove']"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="blogList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="博客编号" align="center" prop="blogId" />
          <el-table-column label="博客标题" align="center" prop="blogTitle" :show-overflow-tooltip="true" />
          <el-table-column label="发布状态" align="center">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.published"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleView(scope.row)"
                v-hasPermi="['system:blog:query']"
              >查看</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:blog:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:user:remove']"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />

      </el-col>
    </el-row>
    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1260px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="96px">
        <el-row>
          <el-col :span="12">
            <el-form-item prop="blogTitle">
              <md-input v-model="form.blogTitle" type="text" icon="el-icon-search" name="blogTitle" placeholder="请输入博客标题">
                标题
              </md-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="博客描述" prop="description">
              <el-input
                type="textarea"
                autosize
                placeholder="请输入描述"
                v-model="form.description">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="博客类型" prop="typeId">
              <treeselect v-model="form.typeId" :options="typeOptions" :show-count="true" placeholder="请选择归属类型" />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="标签">
              <el-select v-model="form.tagIds" multiple placeholder="请选择标签">
                <el-option
                  v-for="item in tagOptions"
                  :key="item.tagId"
                  :label="item.tagName"
                  :value="item.tagId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="22">
          <el-col :span="5">
            <el-form-item label="博客标记">
              <el-radio-group v-model="form.flag">
                <el-radio
                  v-for="dict in flagOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="博客是否发布">
              <el-radio-group v-model="form.published">
                <el-radio
                  v-for="dict in publishedOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="是否开启评论">
              <el-radio-group v-model="form.commentFunction">
                <el-radio
                  v-for="dict in yesNoOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                  >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="是否开启赞赏">
              <el-radio-group v-model="form.appreciateFunction">
                <el-radio
                  v-for="dict in yesNoOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="博客封面">
              <el-input v-model="form.firstPicture" placeholder="请输入首图地址"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="博客内容">
              <mavon-editor
                :boxShadow="false"
                height="100px"
                v-model="form.blogContent" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="viewOpen">

    </el-dialog>

  </div>
</template>

<script>
import { listBlog, addBlog, updateBlog, changeBlogPublishedStatus, delBlog, getBlog } from '@/api/system/blog'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { treeselect } from '@/api/system/blogType'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import MdInput from '@/components/MDinput'

export default {
  name: "Blog",
  components: {
    Treeselect,
    mavonEditor,
    MdInput
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 博客表格数据
      blogList: null,
      // 弹出层标题
      title: "",
      // 类型树选项
      typeOptions: undefined,
      // 博客发布状态选择
      publishedOptions: [],
      // 系统是否选择
      yesNoOptions: [],
      // 博客标记选择
      flagOptions: [],
      // 标签选择
      tagOptions: [],
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层博客浏览
      viewOpen: false,
      // 预览博客
      blog: '',
      // 类型名称
      typeName: undefined,
      // 日期范围
      dateRange: [],
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        searchValue: undefined,
        published: undefined,
        typeId: undefined
      },
      // 表单验证
      rules: {
        blogTitle: [
          { required: true, message: "博客标题不能为空", trigger: "blur" }
        ],
        description: [
          { required: true, message: "博客描述不能为空", trigger: "blur" }
        ],
        typeId: [
          { required: true, message: "归属类型不能为空", trigger: "change" }
        ],
        blogContent: [
          { required: true, message: "博客内容不能为空", trigger: "blur" }
        ]
      }
    }
  },
  watch: {
    // 根据名称筛选类型树
    typeName(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.getList()
    this.getTreeselect()
    this.getDicts('sys_blog_published').then(response => {
      this.publishedOptions = response.data
    })
    this.getDicts('sys_yes_no').then(response => {
      this.yesNoOptions = response.data
    })
    this.getDicts('sys_blog_flag').then(response => {
      this.flagOptions = response.data
    })
  },
  methods: {
    /** 获取博客列表 */
    getList() {
      this.loading = true
      listBlog(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.blogList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询类型下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.typeOptions = response.data
      })
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.typeId = data.id
      this.getList()
    },
    // 修改博客发布状态
    handleStatusChange(row) {
      let text = row.published === '0' ? '发布' : '私密'
      this.$confirm('确认要' + text + ' ' + row.blogTitle + '博客吗?', '警告', {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return changeBlogPublishedStatus(row.blogId, row.published)
      }).then(() => {
        this.msgSuccess(text + '成功')
      }).catch(function() {
        row.published = row.published === '0' ? '1' : '0'
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        blogId: undefined, // 博客id
        blogTitle: undefined, // 博客标题
        description: undefined, // 博客描述
        blogContent: undefined, // 博客内容
        published: '1', // 是否发布
        firstPicture: undefined, // 博客首图
        flag: '0', // 博客标记（0表示原创，1表示转载）
        typeId: undefined, // 类型id
        appreciateFunction: 'Y', // 是否开启赞赏
        commentFunction: 'Y', // 是否开启评论功能
        tagIds: [] // 标签组
      }
    },
    /** 搜索按钮 */
    handleQuery() {
      this.queryParams.pageSize = 1
      this.getList()
    },
    /** 重置按钮 */
    resetQuery() {
      this.dateRange = []
      this.queryParams = {
        searchValue: undefined,
        published: undefined,
        typeId: undefined
      }
      this.getList()
    },
    // 多选框中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.blogId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增博客按钮 */
    handleAdd() {
      this.reset()
      this.getTreeselect()
      getBlog().then(response => {
        this.tagOptions = response.tags
        this.open = true
        this.title = '添加博客'
      })
    },
    /** 查看博客按钮 */
    handleView() {
      this.viewOpen = true
    },
    /** 修改博客按钮 */
    handleUpdate(row) {
      this.reset()
      this.getTreeselect()
      const blogId = row.blogId || this.ids
      getBlog(blogId).then(response => {
        console.log(response)
        this.form = response.data
        this.tagOptions = response.tags
        this.form.tagIds = response.tagIds
        this.open = true
        this.title = '修改博客'
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      console.log(this.form)
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.blogId != undefined) {
            updateBlog(this.form).then(() => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addBlog(this.form).then(() => {
              this.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除博客按钮 */
    handleDelete() {

    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.v-note-wrapper .v-note-panel > div {
  max-height: 500px;
}
</style>
