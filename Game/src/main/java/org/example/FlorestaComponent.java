package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.utilitarios.FimDeJogo;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;

public class FlorestaComponent {

    private Rectangle barra_vida_floresta;

    private int vida_da_floresta = 30;

    public FlorestaComponent() {

        barra_vida_floresta = new Rectangle(300, 50, Color.BLUE);
        barra_vida_floresta.setX(100);
        barra_vida_floresta.setY(100);
        getGameScene().addUINode(barra_vida_floresta);
    }

    public void tomar_dano() {
        vida_da_floresta -= 3;
        barra_vida_floresta.setWidth(barra_vida_floresta.getWidth() - 30);
        if (vida_da_floresta <= 0) {
            FimDeJogo.terminar();
        }
    }

    public void recuperar_vida_da_floresta() {
        vida_da_floresta += 5;
        if(barra_vida_floresta.getWidth() > 250) {
            barra_vida_floresta.setWidth(barra_vida_floresta.getWidth() +(300 - barra_vida_floresta.getWidth()));
        } else {
            barra_vida_floresta.setWidth(barra_vida_floresta.getWidth() + 50);
        }
    }
}
