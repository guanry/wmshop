package net.watermelon.core;

public interface SearchBean {
	// 返回SQL,每个查询的类只要根据自己的情况完成getSQL函数就可以了
	String getSQL();

}
