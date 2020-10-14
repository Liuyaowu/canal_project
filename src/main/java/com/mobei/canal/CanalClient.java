package com.mobei.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Canal监听客户端
 */
public class CanalClient {
    private static String SERVER_ADDRESS = "192.168.10.128";
    private static Integer PORT = 11111;
    private static String DESTINATION = "example";
    private static String USERNAME = "";
    private static String PASSWORD = "";

    public static void main(String[] args) {
        CanalConnector canalConnector = CanalConnectors.newSingleConnector(new InetSocketAddress(SERVER_ADDRESS, PORT), DESTINATION, USERNAME, PASSWORD);
        canalConnector.connect();

        // 订阅
        canalConnector.subscribe(".*\\..*");
        // 回滚
        canalConnector.rollback();

        for (; ;) {
            // 获取指定数量的数据, 但是不确认
            Message message = canalConnector.getWithoutAck(100);
            // 消息id
            long msgId = message.getId();

            if (msgId != -1) {
                System.out.println("batchId --> " + msgId);
                printEntity(message.getEntries());

//                canalConnector.ack(msgId);
//                canalConnector.rollback(msgId);
            }
        }
    }

    private static void printEntity(List<CanalEntry.Entry> entries) {
        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() != CanalEntry.EntryType.ROWDATA) {
                continue;
            }
            try {
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                    // 如果希望监听多种事件可以手动增加case
                    switch (rowChange.getEventType()) {
                        case INSERT:
                            String tableName = entry.getHeader().getTableName();
                            // 测试users表进行映射处理
                            UserDTO userDTO = CanalDataHandler.convertToBean(rowData.getAfterColumnsList(), UserDTO.class);
                            System.out.println(userDTO);
                            System.out.println("this is INSERT \n");
                            break;
                        case DELETE:
                            UserDTO deleteUserDTO = CanalDataHandler.convertToBean(rowData.getAfterColumnsList(), UserDTO.class);
                            System.out.println(deleteUserDTO);
                            System.out.println("this is DELETE \n");
                            break;
                        case UPDATE:
                            UserDTO updateUserDTO = CanalDataHandler.convertToBean(rowData.getAfterColumnsList(), UserDTO.class);
                            System.out.println(updateUserDTO);
                            System.out.println("this is UPDATE \n");
                            break;
                        default:
                            break;
                    }
                }












            } catch (Exception exception) {

            }
        }
    }

}
