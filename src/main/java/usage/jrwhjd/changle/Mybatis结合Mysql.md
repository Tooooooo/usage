# Mybatis

## 1.自动获取生成的主键ID
    首先，如果你的数据库支持自动生成主键的字段（比如 MySQL 和 SQL Server），那么你可以设置 useGeneratedKeys=”true”，然后再把 keyProperty 设置到目标属性上就OK了。例如，如果上面的 Author 表已经对 id 使用了自动生成的列类型，那么语句可以修改为:
	<insert id="insertAuthor" useGeneratedKeys="true" keyProperty="id">
	  insert into Author (username,password,email,bio)
	  values (#{username},#{password},#{email},#{bio})
	</insert>
	如果你的数据库还支持多行插入, 你也可以传入一个Authors数组或集合，并返回自动生成的主键。
	<insert id="insertAuthor" useGeneratedKeys="true" keyProperty="id">
	  insert into Author (username, password, email, bio) values
	  <foreach item="item" collection="list" separator=",">
		(#{item.username}, #{item.password}, #{item.email}, #{item.bio})
	  </foreach>
	</insert>
	
## 2.结合mysql批量修改语句示例
    <update id="batchNotAccept" parameterType="java.util.List">
		update wifi_records set
		accept_status = 
		<foreach collection="list" item="item" index="index" separator=" " open="case wifi_id" close="end">
      		when #{item.wifiId} then #{item.acceptStatus}
  		</foreach>
  		,acceptance_man_id = 
  		<foreach collection="list" item="item" index="index" separator=" " open="case wifi_id" close="end">
      		when #{item.wifiId} then #{item.acceptanceManId}
  		</foreach>
  		where wifi_id in 
  		<foreach collection="list" index="index" item="item" separator="," open="(" close=")">
      		#{item.wifiId}
  		</foreach>
	</update>
	
## 3.结合mysql批量插入语句示例
    <insert id="insertModifyRecords" parameterType="java.util.List">
		INSERT INTO modify_records 
		(
			wifi_id,operation_time,operator_id
		)
		VALUES 
		<foreach collection="list" item="item" index="index" separator=",">
			( 
				#{item.wifiId},#{item.operationTime},#{item.operatorId}
			)
		</foreach>
	</insert>
	
## 4.在使用mybaitis传参数的时候，如果仅传入一个类型为String的参数，那么在 xml文件中应该使用_parameter来代替参数名。
    正确的写法:
        <select id="isCargoBillNoExist" resultType="java.lang.Integer">
            select count(1) from t_entry_cargo_receiver_info
            where 1=1
            <if test="_parameter != null" >
				and cargo_bill_no = #{_parameter,jdbcType=VARCHAR}
            </if>
        </select>
        
	错误的写法：
        <select id="isCargoBillNoExist" resultType="java.lang.Integer">
            select count(1) from t_entry_cargo_receiver_info
            where 1=1
            <if test="id != null" >
				and cargo_bill_no = #{id,jdbcType=VARCHAR}
            </if>
        </select>
        
	也可以在mapper的接口中，给这个方法的参数加上@Param(value=“id”)，这样就能在.xml中使用#{id,jdbcType=VARCHAR} 了。
	如：public Object getObjById(@Param("id)String id);这样也是可以的。
	
## 5.MyBatis 提供了 choose 元素，它有点像 Java 中的 switch 语句。choose, when, otherwise语句的应用示例
    <select id="selectUserByChoose" resultType="com.ys.po.User" parameterType="com.ys.po.User">
      select * from user
      <where>
          <choose>
              <when test="id !='' and id != null">
                  id=#{id}
              </when>
              <when test="username !='' and username != null">
                  and username=#{username}
              </when>
              <otherwise>
                  and sex=#{sex}
              </otherwise>
          </choose>
      </where>
	</select>
    也就是说，这里我们有三个条件，id,username,sex，只能选择一个作为查询条件
	如果 id 不为空，那么查询语句为：select * from user where  id=?
	如果 id 为空，那么看username 是否为空，如果不为空，那么语句为 select * from user where  username=?;
    如果 username 为空，那么查询语句为 select * from user where sex=?

## 6.trim标记是一个格式化的标记，可以完成set或者是where标记的功能
### ①、用 trim 改写上面第二点的 if+where 语句
    <select id="selectUserByUsernameAndSex" resultType="user" parameterType="com.ys.po.User">
        select * from user
        <!-- <where>
            <if test="username != null">
               username=#{username}
            </if>            
            <if test="username != null">
               and sex=#{sex}
            </if>
        </where>  -->
        <trim prefix="where" prefixOverrides="and | or">
            <if test="username != null">
               and username=#{username}
            </if>
            <if test="sex != null">
               and sex=#{sex}
            </if>
        </trim>
    </select>
	prefix：前缀　　　　　　
    prefixoverride：去掉第一个and或者是or
### ②、用 trim 改写上面第三点的 if+set 语句
	<!-- 根据 id 更新 user 表的数据 -->
    <update id="updateUserById" parameterType="com.ys.po.User">
        update user u
            <!-- <set>
                <if test="username != null and username != ''">
                    u.username = #{username},
                </if>
                <if test="sex != null and sex != ''">
                    u.sex = #{sex}
                </if>
            </set> -->
            <trim prefix="set" suffixOverrides=",">
                <if test="username != null and username != ''">
                    u.username = #{username},
                </if>
                <if test="sex != null and sex != ''">
                    u.sex = #{sex},
                </if>
            </trim>
         where id=#{id}
    </update>
	suffix：后缀　　
    suffixoverride：去掉最后一个逗号（也可以是其他的标记，就像是上面前缀中的and一样）

## 7.#{}和${}的区别是什么？

    #{}是预编译处理，${}是字符串替换。
    Mybatis在处理#{}时，会将sql中的#{}替换为?号，调用PreparedStatement的set方法来赋值；
    Mybatis在处理${}时，就是把${}替换成变量的值。
    使用#{}可以有效的防止SQL注入，提高系统安全性。
    
## 8.当实体类中的属性名和表中的字段名不一样 ，怎么办？
### 第1种： 通过在查询的sql语句中定义字段名的别名，让字段名的别名和实体类的属性名一致。
	<select id=”selectorder” parametertype=”int” resultetype=”me.gacl.domain.order”>
       select order_id id, order_no orderno ,order_price price form orders where order_id=#{id};
    </select>
### 第2种： 通过<resultMap>来映射字段名和实体类属性名的一一对应的关系。
	<select id="getOrder" parameterType="int" resultMap="orderresultmap">
        select * from orders where order_id=#{id}
    </select>
	<resultMap type=”me.gacl.domain.order” id=”orderresultmap”>
        <!–用id属性来映射主键字段–>
        <id property=”id” column=”order_id”> 
        <!–用result属性来映射非主键字段，property为实体类属性名，column为数据表中的属性–>
        <result property = “orderno” column =”order_no”/>
        <result property=”price” column=”order_price” />
    </reslutMap>
    
## 9.在mapper中如何传递多个参数?
### （1）第一种：
	//DAO层的函数
	Public UserselectUser(String name,String area);  
	//对应的xml,#{param1}代表接收的是dao层中的第一个参数，#{param2}代表dao层中第二参数，更多参数一致往后加即可。
	<select id="selectUser"resultMap="BaseResultMap">  
		select *  fromuser_user_t   whereuser_name = #{param1} anduser_area=#{param2}  
	</select>  
### （2）第二种： 使用 @param 注解:
	public interface usermapper {
	   user selectuser(@param(“username”) string username,@param(“hashedpassword”) string hashedpassword);
	}
	然后,就可以在xml像下面这样使用(推荐封装为一个map,作为单个参数传递给mapper):
	<select id=”selectuser” resulttype=”user”>
			 select id, username, hashedpassword
			 from some_table
			 where username = #{username}
			 and hashedpassword = #{hashedpassword}
	</select>
### （3）第三种：多个参数封装成map
	try{
	//映射文件的命名空间.SQL片段的ID，就可以调用对应的映射文件中的SQL
	//由于我们的参数超过了两个，而方法中只有一个Object参数收集，因此我们使用Map集合来装载我们的参数
	Map<String, Object> map = new HashMap();
		 map.put("start", start);
		 map.put("end", end);
		 return sqlSession.selectList("StudentID.pagination", map);
	 }catch(Exception e){
		 e.printStackTrace();
		 sqlSession.rollback();
		throw e; }
	finally{
	 MybatisUtil.closeSqlSession();
	 }
