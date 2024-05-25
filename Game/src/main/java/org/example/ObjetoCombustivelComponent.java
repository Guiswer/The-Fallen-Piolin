package org.example;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.particle.ParticleComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.utilitarios.FimDeJogo;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

<<<<<<< HEAD
public class ObjetoCombustivelComponent extends Component {

    private static FlorestaComponent florestaComponente = new FlorestaComponent();

=======

public class ObjetoCombustivelComponent extends Component {

    private static FlorestaComponent florestaComponente = new FlorestaComponent();
>>>>>>> 9dc593f (add adjusts)
    private int contadorDeParticulas = 0;


    public void tomaDano() {
        florestaComponente.tomar_dano();
        getEntity().getComponent(ParticleComponent.class).getEmitter().setNumParticles(++contadorDeParticulas);
    }

    public void recuperarVida() {
        florestaComponente.recuperar_vida_da_floresta();
        getEntity().getComponent(ParticleComponent.class).getEmitter().setNumParticles(0);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 9dc593f (add adjusts)
