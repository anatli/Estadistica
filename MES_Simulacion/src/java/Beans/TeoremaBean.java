/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

/**
 *
 * @author Asus
 */
@Named(value = "teoremaBean")
@SessionScoped
public class TeoremaBean implements Serializable{

    private List<Integer> binos;
    private List<Double> nuevaMuestra;
    private double media,varianza,prob;
    private CartesianChartModel combinedModel;
    private int n,n2;
    
    public TeoremaBean() {
    }
    
    //Métodos
    
    public String iniciar(List<Integer> lista,double m,double v, int n, int n2,double p) throws IOException {
        this.n=n;
        this.n2=n2;
        prob=p;
        binos=new ArrayList<>();
        binos.addAll(lista);
        nuevaMuestra=new ArrayList<>();
        double valor;
        for(int i : binos){
            valor=(i-m)/Math.sqrt(v);
            nuevaMuestra.add(valor);
        }
        media=1.0*sumListItems(nuevaMuestra)/nuevaMuestra.size();
        varianza=0.0;
        for(double i:nuevaMuestra){
            varianza+=Math.pow(i-media, 2);
        }
        varianza/=nuevaMuestra.size();
        createModel(n2,n);
        FacesContext.getCurrentInstance().getExternalContext().redirect("Teorema.xhtml");
        return "Teorema";
    }
    
    public List<Double> noRepe(List<Double> lista){
        List<Double> res=new ArrayList<>();
        for(double i:lista){
            if(!res.contains(i))res.add(i);
        }
        return res;
    }

    public void createModel(int n2, int n){
        List<Double> noRep=noRepe(nuevaMuestra);
        combinedModel= new BarChartModel();
        BarChartSeries frec = new BarChartSeries();
        frec.setLabel("Frecuencia");
 
        LineChartSeries norm = new LineChartSeries();
        norm.setLabel("Normal");
        norm.setXaxis(AxisType.X2);
        norm.setYaxis(AxisType.Y2);
         
        norm.set("0", 2.5);
        norm.set("A", 5);
        norm.set("B", 10);
        norm.set("C", 20);
        norm.set("D", 40);
        norm.set("E", 80);
        norm.set("F", 40);
        norm.set("G", 20);
        norm.set("H", 10);
        norm.set("I", 5);
        norm.set("J", 2.5);
        
        double freq;
        Collections.sort(noRep);
        for(double i:noRep){
            freq=Collections.frequency(nuevaMuestra, i);
            frec.set(i,freq);
        }
          
        combinedModel.addSeries(frec);
        combinedModel.addSeries(norm);
         
        combinedModel.setTitle("Histograma");
        combinedModel.setLegendPosition("ne");
        combinedModel.setMouseoverHighlight(true);
        combinedModel.setAnimate(true);
        combinedModel.setShowDatatip(true);
        combinedModel.setShowPointLabels(true);
        combinedModel.getAxes().put(AxisType.X, new CategoryAxis());
        combinedModel.getAxes().put(AxisType.X2, new CategoryAxis());
        
        Axis yAxis = combinedModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
                 
        Axis y2Axis = new LinearAxis();
        y2Axis.setMin(0);
        y2Axis.setMax(100);
         
        combinedModel.getAxes().put(AxisType.Y2, y2Axis);
    }
    private double sumListItems(List<Double> lista){
        double res=0;
        res = lista.stream().map((i) -> i).reduce(res, (accumulator, _item) -> accumulator + _item);
        return res;
    }
    
    //Getters y setters

    public CartesianChartModel getCombinedModel() {
        return combinedModel;
    }

    public void setCombinedModel(CartesianChartModel combinedModel) {
        this.combinedModel = combinedModel;
    }

    public List<Integer> getBinos() {
        return binos;
    }

    public void setBinos(List<Integer> binos) {
        this.binos = binos;
    }

    public List<Double> getNuevaMuestra() {
        return nuevaMuestra;
    }

    public void setNuevaMuestra(List<Double> nuevaMuestra) {
        this.nuevaMuestra = nuevaMuestra;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public double getVarianza() {
        return varianza;
    }

    public void setVarianza(double varianza) {
        this.varianza = varianza;
    }
    
    
}
