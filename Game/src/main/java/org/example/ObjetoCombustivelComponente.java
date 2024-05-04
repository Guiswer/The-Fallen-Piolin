package org.example;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.particle.ParticleComponent;
import org.example.utilitarios.FimDeJogo;

public class ObjetoCombustivelComponente extends Component {

    private int vida_do_objeto = 10;


    public void tomaDano() {
        vida_do_objeto--;

        getEntity().getComponent(ParticleComponent.class).getEmitter().setNumParticles(10 - vida_do_objeto);
        if (vida_do_objeto <= 0) {
            // GAME OVER!
            FimDeJogo.terminar();
        }
    }

    public void recuperarVida() {
        vida_do_objeto = 10;
        getEntity().getComponent(ParticleComponent.class).getEmitter().setNumParticles(0);
    }
}
