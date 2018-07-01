/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provace;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Carla
 */
public class Individuo implements Comparable<Individuo>{
    
    //Genotipo + Fenotipo
    private ArrayList<Double> cromossomos;
    //Custo da função objetivo
    Double funcaoObjetivo;
        
    //Valor minimo
    Double minimo;
    //Valor máximo
    Double maximo;
    
    //Numero de variaveis
    Integer nVar;

    public Individuo(Double minimo, Double maximo, Integer nVar) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVar = nVar;
        this.cromossomos = new ArrayList<>();
    }

    public ArrayList<Double> getCromossomos() {
        return cromossomos;
    }

    public void setCromossomos(ArrayList<Double> cromossomos) {
        this.cromossomos = cromossomos;
    }

    public Double getFuncaoObjetivo() {
        return funcaoObjetivo;
    }

    public void setFuncaoObjetivo(Double funcaoObjetivo) {
        this.funcaoObjetivo = funcaoObjetivo;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public Integer getnVar() {
        return nVar;
    }

    public void setnVar(Integer nVar) {
        this.nVar = nVar;
    }
    
    // Gerar o genótipo
    public void criar() {

        this.cromossomos = new ArrayList<>();

        Random rnd = new Random();
        Double valor;

        for (int i = 0; i < this.getnVar(); i++) {
            valor = this.minimo
                    + (this.maximo - this.minimo)
                    * rnd.nextDouble();
            this.cromossomos.add(valor);
        }
    }

    @Override
    public int compareTo(Individuo o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
