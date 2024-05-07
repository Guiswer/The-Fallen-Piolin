package org.example;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.particle.ParticleComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.example.utilitarios.FimDeJogo;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static org.example.EntityType.COIN;

public class ObjetoCombustivelComponente extends Component {

    private int vida_do_objeto = 10;

    private Entity barra_visual;

    private Rectangle barra_de_vida_visual = new Rectangle(80, 20, Color.PURPLE);;

    public ObjetoCombustivelComponente(double x, double y) {
        System.out.println("spawn barra..." + x + ", " + y);
        barra_visual = spawn("barra_de_vida_objeto_combustivel", x, y);
    }

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
