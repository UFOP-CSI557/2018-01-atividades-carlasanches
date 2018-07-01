/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provace;

/**
 *
 * @author Carla
 */
public class Problema {
    
    public void calcularFuncaoObjetivo(Individuo individuo){
        
        Double somatorio = 0.0;
        Double result = 0.0;
        
        for(Double cromossomo : individuo.getCromossomos()){
            somatorio += cromossomo * Math.sin(Math.sqrt(Math.abs(cromossomo)));
        }
        
        result = 418.9829 * individuo.getCromossomos().size() - somatorio;
        
        individuo.setFuncaoObjetivo(result);
    }
}
