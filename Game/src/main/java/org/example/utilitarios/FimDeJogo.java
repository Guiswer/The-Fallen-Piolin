package org.example.utilitarios;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameController;

// Classe utilitária para definir a tela de fim de jogo
public class FimDeJogo {

    // Método estático para poder ser usado sem instânciar a classe FimDeJogo
    public static void terminar() {
        // Pausa a engine do jogo
        getGameController().pauseEngine();
        Rectangle barra_laranja = new Rectangle(260, 50, Color.DARKORANGE);
        barra_laranja.setX(getAppWidth()/2 - 130);
        barra_laranja.setY(300);

        // botão para fechar o jogo
        Button btn = new Button("FECHAR");
        btn.setTranslateX(getAppWidth()/2 - 125);
        btn.setTranslateY(300);
        btn.setBackground(Background.fill(Color.ORANGE));
        btn.setPrefWidth(250); // Define a largura do botão
        btn.setPrefHeight(40); // Define a altura do botão


        // Definindo evento para víncular ao botão de fechar
        EventHandler<ActionEvent> evento = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                // Finalização do jogo
                FXGL.getGameController().exit();
            }
        };

        btn.setOnAction(evento);

        Text textOptions = FXGL.getUIFactoryService().newText("DERROTA!", Color.WHITE, FontType.GAME, 54);
        textOptions.setTranslateX(getAppWidth()/2 - 100);

        textOptions.setTranslateY(290);
        textOptions.setMouseTransparent(true);

        // Alocando elementos na interface
        getGameScene().addUINode(barra_laranja);
        getGameScene().addUINode(btn);
        getGameScene().addUINode(textOptions);
    }
}
