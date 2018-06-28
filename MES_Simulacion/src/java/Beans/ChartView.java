/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;
 
@ManagedBean
public class ChartView implements Serializable {
 
    private static CartesianChartModel combinedModel;
 
    private String simbol1,simbol2;
    private int val0,val1,val2;
    private double probPuntual,probInterval,probReal,error;
    
    //Métodos
    public void calcularPuntual(int n,int n2, double p){
        probPuntual=((Double)combinedModel.getSeries().get(0).getData().get(val0))/n;
        probReal= probBinomial(val0,n2,p);
        error=Math.abs(probPuntual-probReal);
    }
    public double probBinomial(int val,int n, double p){
        return 1.0*factorial(n)/(factorial(val)*factorial(n-val))*Math.pow(p, val)*Math.pow(1-p, n-val);
    }
    public void calcularInterval(int n, int n2, double p){
        int a,b;
        if(simbol1.equals("[")){
                a=val1;
        }else{
            a=val1+1;
        }
        if(simbol2.equals("]")){
            b=val2;
        }else{
            b=val2-1;
        }
        probReal=0;
        probInterval=0;
        for(int i=a;i<=b;++i){
            probInterval+=((Double)combinedModel.getSeries().get(0).getData().get(i))/n;
            probReal+=probBinomial(i,n2,p);
        }
        error=Math.abs(probInterval-probReal);
    }
    
    public void createCombinedModel(List<Integer> binos, int n2, int N) {
        combinedModel = creaHistograma(binos, n2, N);
    }
    public static CartesianChartModel creaHistograma(List<Integer> binos, int n2, int N){
        
        CartesianChartModel res = new BarChartModel();
 
        BarChartSeries frec = new BarChartSeries();
        frec.setLabel("Frecuencia");
 
        LineChartSeries acumu = new LineChartSeries();
        acumu.setLabel("%acumu");
        acumu.setYaxis(AxisType.Y2);
        double acum=0,freq;
        
        for(int i=0;i<=n2;++i){
            freq=Collections.frequency(binos, i);
            frec.set(i,freq);
            acum+=(freq *100) /N;
            acumu.set(i,acum);
        }
 
        res.addSeries(frec);
        res.addSeries(acumu);
         
        res.setTitle("Histograma");
        res.setLegendPosition("ne");
        res.setMouseoverHighlight(true);
        res.setAnimate(true);
        res.setShowDatatip(true);
        res.setShowPointLabels(true);
        
        Axis yAxis = res.getAxis(AxisType.Y);
        yAxis.setMin(0);
        
        Axis y2Axis = new LinearAxis();
        y2Axis.setMin(0);
        y2Axis.setMax(120);
         
        res.getAxes().put(AxisType.Y2, y2Axis);
        return res;
    }
     
    public void resetear(){
        combinedModel=null;
    }
    public static int factorial(int n) {
        int resultado = 1;
        for (int i = 1; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }

    //Setters y getters

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    
    public double getProbReal() {
        return probReal;
    }

    public void setProbReal(double probReal) {
        this.probReal = probReal;
    }

    public int getVal0() {
        return val0;
    }

    public void setVal0(int val0) {
        this.val0 = val0;
    }

    public int getVal1() {
        return val1;
    }

    public void setVal1(int val1) {
        this.val1 = val1;
    }

    public int getVal2() {
        return val2;
    }

    public void setVal2(int val2) {
        this.val2 = val2;
    }

    
    public double getProbPuntual() {
        return probPuntual;
    }

    public void setProbPuntual(double probPuntual) {
        this.probPuntual = probPuntual;
    }

    public double getProbInterval() {
        return probInterval;
    }

    public void setProbInterval(double probInterval) {
        this.probInterval = probInterval;
    }

    
    public String getSimbol1() {
        return simbol1;
    }

    public void setSimbol1(String simbol1) {
        this.simbol1 = simbol1;
    }

    public String getSimbol2() {
        return simbol2;
    }

    public void setSimbol2(String simbol2) {
        this.simbol2 = simbol2;
    }
    
    
    
    public CartesianChartModel getCombinedModel() {
        return combinedModel;
    }
}
