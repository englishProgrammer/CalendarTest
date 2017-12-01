package com.code;

        import java.util.Calendar;
        import java.util.GregorianCalendar;
        import java.util.Scanner;
        import static org.fusesource.jansi.Ansi.*;
        import static org.fusesource.jansi.Ansi.Color.*;
/**
 * @Author:LUJIPENG
 * @Description:
 * @Date:Created in 16:25 2017/11/19
 * @Modified By:
 */
public class MonthlyCalendar {
    private Calendar calendar = Calendar.getInstance();
    private int printedNumber = 0;   //记录已输出数目，
    private int printeMax =  42;    //每个月的日历最多输出42天的情况
    /**
     * 用户输入日期,并用set来设置日历日期
     * */
    public void inputData(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("请你输入年份：");
        String year = scanner.next();
        System.out.print("请你输入月份：");
        String month = scanner.next();
        try {
            int yearNumber = Integer.parseInt(year);
            int monthNumber = Integer.parseInt(month);
            if (yearNumber <= 0 || monthNumber <= 0){
                throw new MyException("输入负数？你是在搞事情哦！！！");
            }else if (monthNumber >= 13){
                throw new MyException("输入的月份超过12了？检查一下！！！");
            }
            else {
                calendar.set(yearNumber,Integer.parseInt(month)-1,1);
            }
        }catch (MyException me){
            me.show();
            inputData();
        }catch (NumberFormatException ne){
            System.out.println(ansi().eraseScreen().fg(YELLOW).a("你确定你输入的是数字？").reset());
            inputData();
        }
    }

    /**
     * type决定输出的是上个月的部分日历信息还是下个月的部分日历信息或者是本月的日历信息
     * -1代表上个月，0代表本月，1代表下个月
     * start表示开始的日期，number是输出的数目
     * */
    private void printCalendar(int start,int number,int type){
        for (int i = 0; i < number; i++) {
            printDay(start,type);
            start++;
            printedNumber++;
        }
    }

    /**
     * 根据此天是本月的还是其他月的而以不同的颜色输出
     * */
    private void  printDay(int day,int type){
        if (type == 0){     //本月的天数肯定是最多的，因此放到前面以减少判断次数
            System.out.print(day);
        }else if (type == -1){ //上月的天数
            System.out.print(ansi().eraseScreen().fg(RED).a(day).reset());
        }else if (type == 1){   //下个月的天数
            System.out.print(ansi().eraseScreen().fg(GREEN).a(day).reset());
        }
        if ((printedNumber+1)%7 == 0){
            System.out.println();
        }
        else {
            System.out.print("\t");
        }
    }

    /**
     * 得到在该月日历中，上个月的日历信息显示的个数
     * */
    private int getNumberOfPastMonth(){
        int number = 0;
        int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);  //得到本月第一天是星期几
        //  System.out.println(currentDayOfWeek);
        if (currentDayOfWeek > 2){  //第一天是大于星期一小于星期天,因为星期一是2，星期天是1
            number = currentDayOfWeek - 2;
        }
        else if (currentDayOfWeek == 1){ //第一天是星期天
            number = 6;
        }
        else if (currentDayOfWeek == 2){ //第一天是星期一
            number = 7;
        }
        return number;
    }

    /**
     * 输出上个月的日历信息
     * */
    private void showPastCalendar(){
        Calendar past = new GregorianCalendar(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)-1,calendar.get(Calendar.DAY_OF_MONTH));
        int maxDayPastMonth = past.getActualMaximum(Calendar.DATE);
        int number = getNumberOfPastMonth();
        int start = maxDayPastMonth - number + 1;
        printCalendar(start,number,-1);
    }

    /**
     * 输出本月日历信息
     * */
    private void showCurrentCalendar(){
        printCalendar(1,calendar.getActualMaximum(Calendar.DATE),0);
    }

    private void showFeatureCalendar(){
        int number = printeMax - printedNumber;
        printCalendar(1,number,1);
    }

    /**
     * 输出该月的日历，该日历中还包含一部分上个月日历信息与下个月的部分日历信息
     * */
    public void showCalendar(){
        System.out.println("\t<  " + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH)+1) + "月" +"  >");
        System.out.println("一\t"+"二\t"+"三\t"+"四\t"+"五\t"+"六\t"+"日");
        showPastCalendar();
        showCurrentCalendar();
        showFeatureCalendar();
    }
}
