package org.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.utilitarios.FimDeJogo;

import static com.almasb.fxgl.dsl.FXGL.getGameScene;

/*
 Classe de componente para lógica da vida da floresta
 */
public class FlorestaComponent {

    // barra de vida dinâmica - visual
    private Rectangle barra_vida_floresta;

    // vida
    private int vida_da_floresta = 30;

    public FlorestaComponent() {

        // Definindo imagem de borda para a barra de vida das árvores
        Image imagemBarraDeVidaDiretorio = new Image("assets/textures/barra_de_vida_floresta.png");
        ImageView imagemBarraDeVida = new ImageView(imagemBarraDeVidaDiretorio);
        imagemBarraDeVida.setFitHeight(85);

        imagemBarraDeVida.setX(90);
        imagemBarraDeVida.setY(72);
        imagemBarraDeVida.setPreserveRatio(true);

        getGameScene().addUINode(imagemBarraDeVida);

        // define visual da imagem da barra de vida dinâmica
        barra_vida_floresta = new Rectangle(300, 50, Color.BLUE);
        barra_vida_floresta.setX(100);
        barra_vida_floresta.setY(100);
        getGameScene().addUINode(barra_vida_floresta);
    }

    // Lógica para lidar com a perca de vida da floresta
    public void tomar_dano() {
        vida_da_floresta -= 3;
        barra_vida_floresta.setWidth(barra_vida_floresta.getWidth() - 30);
        if (vida_da_floresta <= 0) {
            FimDeJogo.terminar();
        }
    }

    // Lógica para lidar com a recuperação de vida da floresta
    public void recuperar_vida_da_floresta() {
        vida_da_floresta += 5;
        if(barra_vida_floresta.getWidth() > 250) {
            barra_vida_floresta.setWidth(barra_vida_floresta.getWidth() +(300 - barra_vida_floresta.getWidth()));
        } else {
            barra_vida_floresta.setWidth(barra_vida_floresta.getWidth() + 50);
        }
    }
}
