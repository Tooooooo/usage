# Mysql数据库

## 一.Mysql常用函数
### 1.合并字符串函数：concat(str1,str2,str3…)，用于将多个字符串合并成一个字符串，如果传入的值中有null，那么最终结果是null。
### 2.字符串查找函数：find_in_set(str1,str2)，返回字符串str1在str2中的位置，str2包含若干个以逗号分隔的字符串（可以把str2看出一个列表，元素是多个字符串，查找结果是str1在str2这个列表中的索引位置，从1开始）。
### 3.获取指定位置的子串：substring(str,index,len)，从str的index位置截取len个字符。示例：select substring("iamsuperman",4,5); -- super
### 4.字符串去空函数：trim(),去除字符串str两边的空格。
### 5.获取当前日期：curdate()，current_date()。
### 6.获取当前时间：curtime()，current_time()。
### 7.获取当前日期时间：now()。
### 8.adddate()函数：示例：SELECT ADDDATE('1998-01-02', INTERVAL 31 DAY);-- 获取31天后的时间，结果为1998-02-02。函数中的参数"DAY"可以换成"MONTH"或"YEAR"。
### 9.IFNULL(expr1,expr2)函数：如果 expr1 不是 NULL，IFNULL() 返回 expr1，否则它返回 expr2。
### 10.IF(expr1,expr2,expr3)函数，如果 expr1 是TRUE (expr1 <> 0 and expr1 <> NULL)，则 IF()的返回值为expr2; 否则返回值则为 expr3。IF() 的返回值为数字值或字符串值，具体情况视其所在语境而定。
### 11.比较字符串大小函数：strcmp(str1,str2)，用于比较两个字符串的大小。左大于右时返回1，左等于右时返回0，左小于于右时返回-1。
### 12.mysql常用函数梳理链接：
#### 链接地址一  http://www.cnblogs.com/ygj0930/p/5866388.html 
#### 链接地址二 https://www.jb51.net/article/91641.htm

## 二.利用MySQL统计一列中不同值的数量方法示例
### 其中 origin 是用户来源，其中的值有 iPhone 、Android 、Web 三种，现在需要分别统计由这三种渠道注册的用户数量。
### 解决方案1：
	SELECT count(*) FROM user_operation_log WHERE origin = 'iPhone';
	SELECT count(*) FROM user_operation_log WHERE origin = 'Android';
	SELECT count(*) FROM user_operation_log WHERE origin = 'Web';
	用 where 语句分别统计各自的数量。这样查询的量有点多了，如果这个值有 10 个呢，那还得写 10 条相似的语句，很麻烦。
### 解决方案2：
	第一种写法（用 count 实现）
	SELECT count(origin = 'iPhone' OR NULL) AS iPhone,
	count(origin = 'Android' OR NULL) AS Android,
	count(origin = 'Web' OR NULL)  AS Web
	FROM user_operation_log;
	第二种写法（用 sum 实现）
	SELECT sum(if(origin = 'iPhone', 1, 0)) AS iPhone,
	sum(if(origin = 'Android', 1, 0)) AS Android,
	sum(if(origin = 'Web', 1, 0))  AS Web
	FROM user_operation_log;
	第三种写法（改写 sum）
	SELECT sum(origin = 'iPhone') AS iPhone,
	sum(origin = 'Android') AS Android,
	sum(origin = 'Web')  AS Web
	FROM user_operation_log;

## 三.Mysql之count（*）统计查询数量为0的数据
    SELECT g.user_account,g.user_name,g.user_cardno,COUNT(*) num FROM gsuser g
	LEFT JOIN policys p ON p.user_id = g.user_id
	WHERE g.sales_type = 3
	and p.policyflag = 4
	GROUP BY g.user_account
	ORDER BY num;
	上述SQL无法查询num为0的数据
	
	SELECT AA.user_account,AA.user_name,AA.user_cardno,IFNULL(BB.num,0) AS totalNum from
	(
	(SELECT user_account,user_name,user_cardno,user_id FROM gsuser WHERE sales_type = 3 ) AS AA LEFT JOIN
	(SELECT user_id,COUNT(0) num FROM policys WHERE policyflag = 4 GROUP BY user_id) AS BB
	ON AA.user_id = BB.user_id)
	ORDER BY totalNum;
	改进后的SQL可以查询num为0的数据，但查询速度大大减慢。
	
	有时无法查询出统计为0的数据也可能是表连接的方式使用不正确导致，可以尝试把内连接改为左外连接或右外连接尝试解决。
	
## 四.mysql数据库递归查询子集：queryChildren存储过程示例
    BEGIN
	DECLARE sTemp text;
	DECLARE sTempChd text;
	 SET SESSION group_concat_max_len=4294967295 ;
	SET sTemp = '$';
	SET sTempChd =  areaId ;
	 WHILE sTempChd IS NOT NULL DO
	SET sTemp = CONCAT(sTemp,',',sTempChd);
	SELECT group_concat(dept_id) INTO sTempChd FROM para_dept where FIND_IN_SET(parent_id,sTempChd)>0;
	 END WHILE;
			RETURN sTemp;
	END

## 五.mysql生疏语句
### 1.删除表：drop table table_name;
### 2.在指定位置添加字段：
	ADD COLUMN `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id' AFTER `name`;
	alter table tset_table add age int(4) default 20 after id;
### 3.在第一位添加字段:
	ADD COLUMN `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id' FIRST;
	alter table test_table add test int (5) default 4  first;
### 4.ALTER TABLE用来创建普通索引、UNIQUE索引或PRIMARY KEY索引:
	ALTER TABLE table_name ADD INDEX index_name (column_list);
	ALTER TABLE table_name ADD UNIQUE (column_list);
	ALTER TABLE table_name ADD PRIMARY KEY (column_list)
	其中table_name是要增加索引的表名，column_list指出对哪些列进行索引，多列时各列之间用逗号分隔。索引名index_name可选，缺省时，MySQL将根据第一个索引列赋一个名称。另外，ALTER TABLE允许在单个语句中更改多个表，因此可以在同时创建多个索引。
### 5.CREATE INDEX可对表增加普通索引或UNIQUE索引
	CREATE INDEX index_name ON table_name (column_list);
	CREATE UNIQUE INDEX index_name ON table_name (column_list);
	table_name、index_name和column_list具有与ALTER TABLE语句中相同的含义，索引名不可选。另外，不能用CREATE INDEX语句创建PRIMARY KEY索引。
### 6.删除索引:
	DROP INDEX index_name ON talbe_name;
	ALTER TABLE table_name DROP INDEX index_name;
	ALTER TABLE table_name DROP PRIMARY KEY;
	其中，前两条语句是等价的，删除掉table_name中的索引index_name。
	第3条语句只在删除PRIMARY KEY索引时使用，因为一个表只可能有一个PRIMARY KEY索引，因此不需要指定索引名。如果没有创建PRIMARY KEY索引，但表具有一个或多个UNIQUE索引，则MySQL将删除第一个UNIQUE索引。
	
## 六.判断NULL 
    用IS NULL(不能用 =null) 或者 IS NOT NULL(不能用 !=null), SQL语句函数中可以使用ifnull()函数来进行处理，判断空字符用=''或者 <>''来进行处理。
    注意：NULL 其实并不是空值，而是要占用空间，所以mysql在进行比较的时候，NULL 会参与字段比较，所以对效率有一部分影响。

