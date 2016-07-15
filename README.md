# 供应链金融业务交互接口工程 -- @Controller
> 描述：该工程用于编写供应链金融业务与前端交互的相关接口代码

# 1、源码路径命名规范
### com.betterjr.modules.{module name}

## 如：授信管理模块
### com.betterjr.modules.credit

# 2、scf-dubbo-consumer-springmvc.xml配置
### dubbo:annotation package="com.betterjr.modules.{module name}",多个{module name}用逗号","隔开

# 3、spring-context-scf-dubbo-consumer.xml配置
### context:component-scan base-package="com.betterjr.modules.{module name}",多个{module name}用分号";"隔开

# 4、spring-mvc.xml配置
### context:component-scan base-package="com.betterjr.modules.{module name}",多个{module name}用分号";"隔开

# 5、nginx.conf配置
	location /scf-web/ {
					proxy_pass http://xxx.qiejf.com:8080;
					include proxy.conf;
					#proxy_next_upstream error timeout invalid_header http_500 http_502 http_503 http_504;
				}