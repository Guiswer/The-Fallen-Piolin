package org.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.scene.*;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.Body;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Optional;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameController;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

/*
 Classe principal para rodar o programa!
 extende a classe GameApplication da biblioteca FXGL
 responsável por disponibilizar os métodos de configuração
 do jogo
 */
public class Main extends GameApplication {

// Injetando a classe player de forma global
    private Entity player;
    private Entity enemy;
    private int vidaDaFloresta = 30;

    private Rectangle barra_vida_floresta;

// método de configurações usado para
// definir o tamanho da tela entre outros.
    @Override
    protected void initSettings(GameSettings settings) {
        // Configura tamanho da tela
        settings.setWidth(15*70); // 1050
        settings.setHeight(10*70); // 700
        //System.out.println(settings.getMenuKey());

        // Habilita os menus de configurações dentro do jogo
        settings.setGameMenuEnabled(true);
        settings.setDeveloperMenuEnabled(true);

        // Inicia a cena de carregamento
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public LoadingScene newLoadingScene() {
                return new MainLoadingScene();
            }

            /*@Override
            public FXGLMenu newGameMenu() {
                //return new SimpleGameMenu();
                return new MyPauseMenu();
            }*/
        });

        // Modo da aplicação (Desenvolvimento, Debug ou final) apenas algo visual
        settings.setApplicationMode(ApplicationMode.DEVELOPER);

        //settings.getCollisionDetectionStrategy();
        //settings.getEnabledMenuItems();
    }

/*
    Método responsável por criar os atalhos das teclas
    e definir as ações
*/
    @Override
    protected void initInput() {

        //Mover personagem para a esquerda
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        //Mover personagem para a direita
        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        //Pular com o personagem
        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jump();
            }
        }, KeyCode.W, VirtualButton.A);

        //Atirar penas com o personagem
        getInput().addAction(new UserAction("Disparar pena") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).shoot();
            }
        }, MouseButton.PRIMARY);

        getInput().addAction(new UserAction("Disparar água") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).dispararAgua();
            }
        }, MouseButton.SECONDARY);

        getInput().addAction(new UserAction("PAUSE") {
            @Override
            protected void onActionBegin() {

               // FXGL.play("2024-05-01_19-17-10_online-audio-converter.com.wav");
                getGameController().pauseEngine();
            }
        }, KeyCode.F);

        getInput().addAction(new UserAction("RESUME") {
            @Override
            protected void onActionBegin() {
                getGameController().resumeEngine();
            }
        }, KeyCode.G);


        //Configurações de entradas de dados para testar o Espalha Lixo
        getInput().addAction(new UserAction("Move espalha lixo para a esquerda!") {
            @Override
            protected void onActionBegin() {
                enemy.getComponent(EnemyComponent.class).moveParaEsquerda();
            }
        }, KeyCode.J);

        getInput().addAction(new UserAction("Move espalha lixo para a direita!") {
            @Override
            protected void onActionBegin() {
                enemy.getComponent(EnemyComponent.class).moveParaDireita();
            }
        }, KeyCode.K);
        getInput().addAction(new UserAction("Disparar com Espalha Lixo") {
            @Override
            protected void onActionBegin() {
                Entity enemy = getGameWorld().getSingleton(EntityType.ENEMY);

                Optional<Entity> entidadeMaisProxima = getGameWorld().getClosestEntity(enemy, (e) -> {
                    return true;
                });


                enemy.getComponent(EnemyComponent.class).atirar(entidadeMaisProxima.get());
            }
        }, KeyCode.L);

    }


/*
 Método acionado antes do init para definir o som de fundo
*/
    @Override
    protected void onPreInit() {
        // Modificando volume inicial
        getSettings().setGlobalMusicVolume(0.8);

        // Definindo som de fundo
        loopBGM("backsound.wav");

    }

    /*
    Método para iniciar o jogo adicionando a classe de fábrica (factory), também é reposponsável por
    invocar o plano de fundo e jogador e configurar a tela para se vincular ao jogador
     */
    @Override
    protected void initGame() {
        FXGL.play("mudado.wav");

        /*
         Vinculando a classe de fábrica que é responsável por saber
         as configurações de criação de cada entidade
         e as suas características quando invocada no jogo
        */
        getGameWorld().addEntityFactory(new MainFactory());

        // Invocando plano de fundo
        spawn("background");
        // Definindo o mapa
        setLevelFromMap("tmx/map-remastered8.tmx");


        // PIOLIN
        player = spawn("player", 0, 600);
        // Ajustar o amortecimento linear do corpo físico para evitar o quique
        // deixa o personagem planando quando pula
        Body bodyPiolin = player.getComponent(PhysicsComponent.class).getBody();
        bodyPiolin.setLinearDamping(10.0f);

        // Espalha Lixo
        enemy = spawn("enemy", 1200, 600);
        Body bodyEspalhaLixo = enemy.getComponent(PhysicsComponent.class).getBody();
        bodyEspalhaLixo.setLinearDamping(10.0f);


        // Configurações de tela (viewport) para se vincular ao jogador
        Viewport viewport = getGameScene().getViewport();
        viewport.setBounds(-1500, -200, 2560, 1200);
        viewport.bindToEntity(player, getAppWidth() / 2, 450);
        viewport.setZoom(2.5);
        viewport.setLazy(true);
    }


/*
    Método responsável por definir as configurações
    de física do jogo como a gravidade para as entidades
    e as ações de colisão entre entidades
 */
    @Override
    protected void initPhysics() {
        // Definindo gravidade
        getPhysicsWorld().setGravity(0, 760);

        // Definindo a colisão da pena com o inimigo
        onCollisionBegin(EntityType.DISPARO_DE_PENA_JOGADOR, EntityType.ENEMY, (bullet, a) -> {
            bullet.removeFromWorld();
            enemy.getComponent(EnemyComponent.class).tomaDano();
        });

        onCollisionBegin(EntityType.DISPARO_INIMIGO, EntityType.JOGADOR, (tiro, player) -> {
           tiro.removeFromWorld();
           player.getComponent(PlayerComponent.class).tomaDano();
        });

        onCollisionBegin(EntityType.DISPARO_INIMIGO, EntityType.OBJETO_COMBUSTIVEL, (tiro, objetoCombustivel) -> {
           tiro.removeFromWorld();
           System.out.println("Acertou objeto combustivel");
           objetoCombustivel.getComponent(ObjetoCombustivelComponent.class).tomaDano();
        });

        onCollisionBegin(EntityType.DISPARO_DE_AGUA_JOGADOR, EntityType.OBJETO_COMBUSTIVEL, (tiro, objetoCombustivel) -> {
            tiro.removeFromWorld();
            objetoCombustivel.getComponent(ObjetoCombustivelComponent.class).recuperarVida();
        });
    }


    // Método main padrão chamando as configurações da biblioteca FXGL
    public static void main(String[] args) {
        launch(args);
    }

}