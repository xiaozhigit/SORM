# SORM
ORM框架
使用方法：
1.在src下建立db.rpoperties,可以使用源码下面的db.rpoperties修改其数据库及其连接，修改生成的实体类的路径。
2.使用方法调用TableContext.updateJavaPOFile()；生成实体类。
3.创建查询接口 Query q=QueryFactory.createQuery()后就可以使用相关的查询方法，详情请看demo
4.每张表只能有一个主键，不能处理多个主键的情况
5.po尽量使用包装类不要使用基本数据类型
