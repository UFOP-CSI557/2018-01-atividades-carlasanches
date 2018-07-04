/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passeiodocavalo;

/**
 *
 * @author Carla
 */
public class Problema {
    
    public void calcularFuncaoObjetivo(Individuo individuo){
        
        Integer soma = 0;
        
        individuo.decodificar();
        individuo.calcularMovimento();
                
        for(Integer var : individuo.getVariaveis()){
            if(var == -1){              
                break;
            }
            else soma++;
        }
        
        individuo.setFuncaoObjetivo(soma);        
    }
    
}
