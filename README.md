# ssm_template


# 2017.6.1    更新 
推荐使用springboo,省去xml配置烦恼 https://github.com/rainzhu/springbootdemo



spring+springmvc+mybatis的配置模板，简单容易上手，再也不用为搭建环境犯愁，开源给大家，附一份文档说明

从零配置安装SpringMVC+Spring+Mybatis

 

Maven导入ssm_template的项目

如果是自己手动创建：

New –> project->Maven Project->选择项目路径->选择maven-archetype-webapp->输入Group id和artifact id然后完成创建

 

推荐直接导入：

File->import->Existing Maven Projects->选择项目路径即可，maven会自动帮你build所依赖的环境

 

结构说明

ssm_template                                    ----------------------------项目目录

         src/main/resources                           ---------------------------配置文件相关目录

                  config.properties                        ---------------------------db等配置文件信息

                   log4j.properties                       ---------------------------日志信息

spring-mvc.xml                           ---------------------------springmvc配置文件

                   spring-mybatis.xml                    ---------------------------spring和mybatis的配置整合文件

                   spring.xml                                    ----------------------------spring的配置文件

         src/main/java                                       ----------------------------逻辑代码

                   ssm.controller                             ----------------------------控制其部分代码

                   ssm.dao                                        ----------------------------表型对象

                   ssm.mapper                                ----------------------------操作db层接口

ssm.mapper.imp                        -----------------------------操作db层mybatis映射

ssm.service                                  ----------------------------业务逻辑层接口

ssm.service.imp                         ----------------------------业务具体实现接口

         src/test/java

src                                                                    

         main

                   webapp                                         ---------------------------访问资源文件夹

                            views                                    ---------------------------动态资源目录

                            web.xml                               --------------------------webapp配置信息

target                                                               ---------------------------maven打包文件

pom.xml                                                          ---------------------------maven依赖.jar配置信息

 

项目加载流程如上面我所描述的，再根据tomcat的日志去跟踪，很方便就可以搭建开发环境，前提是你对于spring和mybatis已经有了理论的基础!

 

在我走完整个流程之后，我发现spring，springmvc和mybatis是通过xml文件联系起来的，也就是说理解项目中的4个xml文件对你加深spring架构的理解有一定的帮助。

 github源码地址 https://github.com/rainzhu/ssm_template.git，觉得不错记得给star哦
