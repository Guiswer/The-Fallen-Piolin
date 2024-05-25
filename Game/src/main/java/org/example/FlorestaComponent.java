package org.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.utilitarios.FimDeJogo;

import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;

public class FlorestaComponent {

<<<<<<< HEAD
    private Rectangle barra_vida_floresta;

=======
    // Definindo barra de vida
    private Rectangle barra_vida_floresta;
>>>>>>> 9dc593f (add adjusts)
    private int vida_da_floresta = 30;

    public FlorestaComponent() {

        // Definindo imagem de borda para a barra de vida das árvores
        Image imagemBarraDeVidaDiretorio = new Image("assets/textures/barra_de_vida_floresta.png");
        ImageView imagemBarraDeVida = new ImageView(imagemBarraDeVidaDiretorio);
        imagemBarraDeVida.setFitHeight(85);

<<<<<<< HEAD
        imagemBarraDeVida.setX(90);
        imagemBarraDeVida.setY(72);
=======
        imagemBarraDeVida.setX(getAppWidth()/2 - 125);
        imagemBarraDeVida.setY(20);
>>>>>>> 9dc593f (add adjusts)
        imagemBarraDeVida.setPreserveRatio(true);

        getGameScene().addUINode(imagemBarraDeVida);

<<<<<<< HEAD
        barra_vida_floresta = new Rectangle(300, 50, Color.BLUE);
        barra_vida_floresta.setX(100);
        barra_vida_floresta.setY(100);
=======
        barra_vida_floresta = new Rectangle(247, 50, Color.LIGHTBLUE);
        barra_vida_floresta.setX(getAppWidth()/2 - 115);
        barra_vida_floresta.setY(48);
>>>>>>> 9dc593f (add adjusts)
        getGameScene().addUINode(barra_vida_floresta);
    }

    public void tomar_dano() {
<<<<<<< HEAD
        vida_da_floresta -= 3;
        barra_vida_floresta.setWidth(barra_vida_floresta.getWidth() - 30);
        if (vida_da_floresta <= 0) {
            FimDeJogo.terminar();
=======

        // Tira 3 de dano
        vida_da_floresta -= 3;
        barra_vida_floresta.setWidth(barra_vida_floresta.getWidth() - 30);

        if (vida_da_floresta <= 0) {
            FimDeJogo.terminarLoser();
>>>>>>> 9dc593f (add adjusts)
        }
    }

    public void recuperar_vida_da_floresta() {
<<<<<<< HEAD
        vida_da_floresta += 5;
        if(barra_vida_floresta.getWidth() > 250) {
            barra_vida_floresta.setWidth(barra_vida_floresta.getWidth() +(300 - barra_vida_floresta.getWidth()));
        } else {
            barra_vida_floresta.setWidth(barra_vida_floresta.getWidth() + 50);
        }
    }
}
=======

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
>>>>>>> 9dc593f (add adjusts)
