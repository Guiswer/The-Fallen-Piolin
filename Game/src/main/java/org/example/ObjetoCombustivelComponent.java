package org.example;


import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.particle.ParticleComponent;


// Classe de componente para lógica de objetos combustíveis
public class ObjetoCombustivelComponent extends Component {

    private static FlorestaComponent florestaComponente = new FlorestaComponent();

    private int contadorDeParticulas = 0;


    public void tomaDano() {
        florestaComponente.tomar_dano();
        getEntity().getComponent(ParticleComponent.class).getEmitter().setNumParticles(++contadorDeParticulas);
    }

    public void recuperarVida() {
        florestaComponente.recuperar_vida_da_floresta();
        getEntity().getComponent(ParticleComponent.class).getEmitter().setNumParticles(0);
    }
}
