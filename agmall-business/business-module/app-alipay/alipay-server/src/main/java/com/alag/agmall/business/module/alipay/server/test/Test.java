package com.alag.agmall.business.module.alipay.server.test;

public class Test {

    public static void main(String[] args) throws Exception {
//        ExecutorService executorService = Executors.newFixedThreadPool(100);
//        AlipayService alipayService = new AlipayServiceImpl();
//        File file1 = new File("/Users/alag/Desktop/file/orderSqlFile");
//        File file2 = new File("/Users/alag/Desktop/file/orderIDFile");
//        long min = 100000000000000000L;
//        long max = 999999999999999999L;
////
////        BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1,true), "utf-8"));
////        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2,true), "utf-8"));
////        String rangeLong = "";
////        String oID2 = "";
//
////        for (int i = 0; i < 100; i++) {
////            rangeLong = min + (((long) (new Random().nextDouble() * (max - min))))+"";
////            bw2.write(rangeLong);
////            bw2.newLine();
////            oID2 = "'" + rangeLong + "'";
////            String sql = "insert into mmall_order (order_no, user_id,shipping_id, payment, payment_type,postage, status, payment_time,send_time, end_time, close_time,create_time, update_time) VALUES ("+oID2+",'1','32','32000','1','0','10',NOW(),null,null,null,NOW(),NOW());";
////            bw1.write(sql);
////            bw1.newLine();
////        }
////
////
////        bw1.flush();
////        bw2.flush();
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file2), "utf-8"));
////
////      System.out.println(br.readLine());
//        Map<String, String> paramMap = Maps.newHashMap();
//        String orderNo = "";
//        String tradeNo = "";
//        for (int i = 0; i < 100; i++) {
//            orderNo = br.readLine();
//            tradeNo = min + (((long) (new Random().nextDouble() * (max - min)))) + "";
//            paramMap.put("out_trade_no", orderNo);
//            paramMap.put("trade_no", tradeNo);
//            paramMap.put("trade_status", "20");
//            paramMap.put("gmt_payment", DateTimeUtil.dateToStr(new Date()));
//            alipayService.aliCallback(paramMap);
///*            executorService.submit(new Callable() {
//                @Override
//                public Object call() throws Exception {
//                    return 1;
//                }
//            });
//        }*/
////        executorService.shutdown();
//        }
//
    }

}
