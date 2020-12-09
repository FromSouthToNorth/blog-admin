package vip.hyzt.web.controller.system;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.hyzt.common.annotation.Log;
import vip.hyzt.common.core.controller.BaseController;
import vip.hyzt.common.core.domain.AjaxResult;
import vip.hyzt.common.enums.BusinessType;
import vip.hyzt.common.utils.SecurityUtils;
import vip.hyzt.system.domain.SysGossip;
import vip.hyzt.system.service.ISysGossipService;
import vip.hyzt.common.utils.poi.ExcelUtil;
import vip.hyzt.common.core.page.TableDataInfo;

/**
 * 随笔Controller
 * 
 * @author hyzt
 * @date 2020-12-09
 */
@RestController
@RequestMapping("/system/gossip")
public class SysGossipController extends BaseController
{
    @Autowired
    private ISysGossipService sysGossipService;

    /**
     * 查询随笔列表
     */
    @PreAuthorize("@ss.hasPermi('system:gossip:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysGossip sysGossip)
    {
        startPage();
        List<SysGossip> list = sysGossipService.selectGossipList(sysGossip);
        return getDataTable(list);
    }

    /**
     * 导出随笔列表
     */
    @PreAuthorize("@ss.hasPermi('system:gossip:export')")
    @Log(title = "随笔", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysGossip sysGossip)
    {
        List<SysGossip> list = sysGossipService.selectGossipList(sysGossip);
        ExcelUtil<SysGossip> util = new ExcelUtil<SysGossip>(SysGossip.class);
        return util.exportExcel(list, "gossip");
    }

    /**
     * 获取随笔详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:gossip:query')")
    @GetMapping(value = "/{gossipId}")
    public AjaxResult getInfo(@PathVariable("gossipId") Long gossipId)
    {
        return AjaxResult.success(sysGossipService.selectGossipById(gossipId));
    }

    /**
     * 新增随笔
     */
    @PreAuthorize("@ss.hasPermi('system:gossip:add')")
    @Log(title = "随笔", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysGossip sysGossip)
    {
        sysGossip.setUserId(SecurityUtils.getUserId());
        sysGossip.setCreateBy(SecurityUtils.getUsername());
        return toAjax(sysGossipService.insertGossip(sysGossip));
    }

    /**
     * 修改随笔
     */
    @PreAuthorize("@ss.hasPermi('system:gossip:edit')")
    @Log(title = "随笔", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGossip sysGossip)
    {
        sysGossip.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(sysGossipService.updateGossip(sysGossip));
    }

    /**
     * 删除随笔
     */
    @PreAuthorize("@ss.hasPermi('system:gossip:remove')")
    @Log(title = "随笔", businessType = BusinessType.DELETE)
	@DeleteMapping("/{gossipIds}")
    public AjaxResult remove(@PathVariable Long[] gossipIds)
    {
        return toAjax(sysGossipService.deleteGossipByIds(gossipIds));
    }
}
