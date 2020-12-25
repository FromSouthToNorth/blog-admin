# admin
Jar方式部署
执行命令：java –jar admin.jar 
脚本执行：hy.sh start 启动stop 停止

前端部署
# 打包正式环境
npm run build:prod

# 打包预发布环境
npm run build:stage

构建打包成功之后，会在根目录生成 dist 文件夹，里面就是构建打包好的文件，通常是 ***.js 、***.css、index.html 等静态文件。发布到你的 nginx 或者静态服务器即可，其中的 index.html 是后台服务的入口页面。
