#最简启动配置.

##Server端:

1.指定线程组.  boss/work

2.指定线程IO模型. NioServerSocketChannel

3.添加IO的Chennel处理器

##Client端:
1.添加线程组 work

2.指定线程IO模型.  NioSocketChannel

3.添加客户端的ChannelHandler处理器.