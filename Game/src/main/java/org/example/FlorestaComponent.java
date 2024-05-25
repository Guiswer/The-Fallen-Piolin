package org.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.utilitarios.FimDeJogo;

import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;

public class FlorestaComponent {

    // Definindo barra de vida
    private Rectangle barra_vida_floresta;
    private int vida_da_floresta = 30;

    public FlorestaComponent() {

        // Definindo imagem de borda para a barra de vida das árvores
        Image imagemBarraDeVidaDiretorio = new Image("assets/textures/barra_de_vida_floresta.png");
        ImageView imagemBarraDeVida = new ImageView(imagemBarraDeVidaDiretorio);
        imagemBarraDeVida.setFitHeight(85);

        imagemBarraDeVida.setX(getAppWidth()/2 - 125);
        imagemBarraDeVida.setY(20);
        imagemBarraDeVida.setPreserveRatio(true);

        getGameScene().addUINode(imagemBarraDeVida);

        barra_vida_floresta = new Rectangle(247, 50, Color.LIGHTBLUE);
        barra_vida_floresta.setX(getAppWidth()/2 - 115);
        barra_vida_floresta.setY(48);
        getGameScene().addUINode(barra_vida_floresta);
    }

    public void tomar_dano() {

        // Tira 3 de dano
        vida_da_floresta -= 3;
        barra_vida_floresta.setWidth(barra_vida_floresta.getWidth() - 30);

        if (vida_da_floresta <= 0) {
            FimDeJogo.terminarLoser();
        }
    }

    public void recuperar_vida_da_floresta() {

        // Recupera 5
        vida_da_floresta += 5;

        if(barra_vida_floresta.getWidth() < 247) {
            barra_vida_floresta.setWidth(247);
        }

        else {
            barra_vida_floresta.setWidth(barra_vida_floresta.getWidth() + 50);

            if(barra_vida_floresta.getWidth() > 247) {
                barra_vida_floresta.setWidth(247);
            }
        }
    }
}