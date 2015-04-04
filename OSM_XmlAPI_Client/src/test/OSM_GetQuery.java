package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OSM_GetQuery {

	public static String orderID, View,histID;
	
	public static void main(String[] args) throws Exception {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@183.82.66.180:1521:orcl11203",
				"ordermgmt7221_1", "ordermgmt7221_1");
		Statement stmnt = con.createStatement();
		String sql = "select max(HIST_SEQ_ID) as HIST_SEQ_ID from om_hist$order_header where order_seq_id=53";
		ResultSet rs = stmnt.executeQuery(sql);
		while (rs.next()) {
			histID = rs.getString("HIST_SEQ_ID");
			System.out.println(histID);
		}

	}

	public static String getTaskMnemonic() throws Exception {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@183.82.72.96:1521:orcl11203",
				"ordermgmt7221_1", "ordermgmt7221_1");
		System.out.println(con);
		Statement stmnt = con.createStatement();
	/*	String sql = "select task_mnemonic from om_order_header "
				+ "inner join om_workgroup_order_view on om_workgroup_order_view.order_source_id=om_order_header.order_source_id "
				+ "inner join om_task_order_view on om_task_order_view.order_view_seq_id=om_workgroup_order_view.order_view_seq_id "
				+ "inner join om_task on om_task.task_id=om_task_order_view.task_id where order_seq_id="
				+ orderID;*/
		String sql = "select task_mnemonic from om_order_header "
				+ "inner join om_workgroup_order_view on om_workgroup_order_view.order_source_id=om_order_header.order_source_id "
				+ "inner join om_task_order_view on om_task_order_view.order_view_seq_id=om_workgroup_order_view.order_view_seq_id "
				+ "inner join om_task on om_task.task_id=om_task_order_view.task_id where order_seq_id=45";
		ResultSet rs = stmnt.executeQuery(sql);
		while (rs.next()) {
			View = rs.getString("TASK_MNEMONIC");
			System.out.println(View);

		}
		return View;

	}

	public static String getHistID() throws Exception {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@183.82.72.96:1521:orcl11203",
				"ordermgmt7221_1", "ordermgmt7221_1");
		System.out.println(con);
		Statement stmnt = con.createStatement();
	/*	String sql = "select max(HIST_SEQ_ID) as HIST_SEQ_ID from om_hist$order_header where order_seq_id="+orderID;*/
		String sql = "select max(HIST_SEQ_ID) as HIST_SEQ_ID from om_hist$order_header where order_seq_id="+orderID;
		ResultSet rs = stmnt.executeQuery(sql);
		while (rs.next()) {
			histID = rs.getString("HIST_SEQ_ID");
			System.out.println(histID);

		}
		
		return histID;
	}
	
	

}
