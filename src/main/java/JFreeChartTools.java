import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class JFreeChartTools extends ApplicationFrame {
    public JFreeChartTools(String title, double[] nodes, double[] interpVal, double[] realVal) {

        super(title);

        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries interpolGraphSeries = new XYSeries("interpolate");
        for (int i = 0; i < nodes.length; i++)
            interpolGraphSeries.add(nodes[i], interpVal[i]);
        dataset.addSeries(interpolGraphSeries);

        XYSeries realGraphSeries = new XYSeries("real");
        for (int i = 0; i < nodes.length; i++)
            realGraphSeries.add(nodes[i], realVal[i]);
        dataset.addSeries(realGraphSeries);

        JFreeChart chart = ChartFactory.createScatterPlot("compare interpolate and real: " + title,
                "X", "Y", dataset, PlotOrientation.VERTICAL,true,true,false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        setContentPane(chartPanel);
    }

    public JFreeChartTools(String title, double[] nodes, double[] splineVal, double[] polynVal, double[] realVal) {

        super(title);

        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries splineGraphSeries = new XYSeries("spline");
        for (int i = 0; i < nodes.length; i++)
            splineGraphSeries.add(nodes[i], splineVal[i]);
        dataset.addSeries(splineGraphSeries);

        XYSeries polynGraphSeries = new XYSeries("polyn");
        for (int i = 0; i < nodes.length; i++)
            polynGraphSeries.add(nodes[i], polynVal[i]);
        dataset.addSeries(polynGraphSeries);

        XYSeries realGraphSeries = new XYSeries("real");
        for (int i = 0; i < nodes.length; i++)
            realGraphSeries.add(nodes[i], realVal[i]);
        dataset.addSeries(realGraphSeries);

        JFreeChart chart = ChartFactory.createScatterPlot("compare : " + title,
                "X", "Y", dataset, PlotOrientation.VERTICAL,true,true,false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        setContentPane(chartPanel);
    }
}
