/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Asus
 */
@Named(value = "managedBean")
@SessionScoped
public class ManagedBean implements Serializable{
    //Valores básicos (Num variables, num pruebas Bernoulli, prob de exito)
    private int N,n2;
    private double p;
    //Listas 
    private List<Double> aleatorios;
    private List<Integer> bernoullis, binomiales;
    //Media y varianza
    private double media,varianza;
    private boolean medYvarCalculadas;
    
    public ManagedBean() {
        N=10000;
        n2=10;
        p=0.4;
    }
    
    //Métodos
    public void calcular(){
        List<Double> aleatorios2=new ArrayList<>();
        List<Integer> bs=new ArrayList<>();
        List<Integer> bis=new ArrayList<>();
        for(int i=0;i<N;++i){
            int xi=0;
            for(int j=0;j<n2;++j){
                BigDecimal bd = new BigDecimal(Math.random());
                bd = bd.setScale(10, RoundingMode.HALF_UP);
                aleatorios2.add(bd.doubleValue());
                if(bd.doubleValue()<=p){
                    bs.add(1);
                    xi++;
                }else{
                    bs.add(0);
                }
            }
            bis.add(xi);
        }
        medYvarCalculadas=false;//Cada vez que operamos nuevamente, se resetea el valor
        this.aleatorios = aleatorios2;
        this.bernoullis=bs;
        this.binomiales=bis;
    }
    public void generarMedVar(){
        media=1.0*sumListItems(binomiales)/N;
        varianza=0.0;
        for(int i:binomiales){
            varianza+=Math.pow(i-media, 2);
        }
        varianza/=N;
        this.medYvarCalculadas=true;
    }
    private int sumListItems(List<Integer> lista){
        int res=0;
        res = lista.stream().map((i) -> i).reduce(res, Integer::sum);
        return res;
    }
    
    //setters y getters

    public boolean isMedYvarCalculadas() {
        return medYvarCalculadas;
    }

    public void setMedYvarCalculadas(boolean medYvarCalculadas) {
        this.medYvarCalculadas = medYvarCalculadas;
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

    
    public List<Integer> getBinomiales() {
        return binomiales;
    }

    public void setBinomiales(List<Integer> binomiales) {
        this.binomiales = binomiales;
    }
    
    public List<Integer> getBernoullis() {    
        return bernoullis;
    }
    public void setBernoullis(List<Integer> bernoullis) {
        this.bernoullis = bernoullis;    
    }

    public List<Double> getAleatorios() {
        return aleatorios;
    }

    public void setAleatorios(List<Double> aleatorios) {
        this.aleatorios = aleatorios;
    }

    public int getN() {
        return N;
    }

    public void setN(int N) {
        this.N = N;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n) {
        this.n2 = n;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }
    
}
