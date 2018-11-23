package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance(String suiteName) {
        if (extent == null)
            createInstance(suiteName);

        return extent;
    }

    public static ExtentReports createInstance(String suiteName) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("MM.dd.yyyy");

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter( suiteName+"-"+ft.format(dNow)+".html");
//        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(ft.format(dNow) +"_" +suiteName+"_"+"test_report.html");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Acceptance Tests");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("erer");


        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        return extent;
    }
}
