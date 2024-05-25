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

<<<<<<< HEAD
=======

>>>>>>> 9dc593f (add adjusts)
/*
 Classe principal para rodar o programa!
 extende a classe GameApplication da biblioteca FXGL
 responsável por disponibilizar os métodos de configuração
 do jogo
 */
<<<<<<< HEAD
=======

>>>>>>> 9dc593f (add adjusts)
public class Main extends GameApplication {

// Injetando a classe player de forma global
    private Entity player;
    private Entity enemy;
    private int vidaDaFloresta = 30;

    private Rectangle barra_vida_floresta;

// método de configurações usado para
// definir o tamanho da tela entre outros.
    @Override
<<<<<<< HEAD
    protected void initSettings(GameSettings settings) {
        // Configura tamanho da tela
        settings.setWidth(15*70); // 1050
        settings.setHeight(10*70); // 700
        //System.out.println(settings.getMenuKey());
=======
    protected void initSettings(GameSettings settings){

        // Configura tamanho da tela
        settings.setWidth(15*70); // 1050
        settings.setHeight(10*70); // 700
>>>>>>> 9dc593f (add adjusts)

        // Habilita os menus de configurações dentro do jogo
        settings.setGameMenuEnabled(true);
        settings.setDeveloperMenuEnabled(true);

        // Inicia a cena de carregamento
<<<<<<< HEAD
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
=======
        settings.setSceneFactory(new SceneFactory(){

            @Override
            public LoadingScene newLoadingScene(){
                return new MainLoadingScene();
            }

            /*
            @Override
            public FXGLMenu newGameMenu() {
                //return new SimpleGameMenu();
                return new MyPauseMenu();
            }
            */
>>>>>>> 9dc593f (add adjusts)
        });

        // Modo da aplicação (Desenvolvimento, Debug ou final) apenas algo visual
        settings.setApplicationMode(ApplicationMode.DEVELOPER);

<<<<<<< HEAD
        //settings.getCollisionDetectionStrategy();
        //settings.getEnabledMenuItems();
=======
        // Settings.getCollisionDetectionStrategy();
        // Settings.getEnabledMenuItems();
>>>>>>> 9dc593f (add adjusts)
    }

/*
    Método responsável por criar os atalhos das teclas
    e definir as ações
*/
    @Override
    protected void initInput() {

<<<<<<< HEAD
        //Mover personagem para a esquerda
=======
        // Mover personagem para a esquerda
>>>>>>> 9dc593f (add adjusts)
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
<<<<<<< HEAD
        }, KeyCode.A, VirtualButton.LEFT);

        //Mover personagem para a direita
=======
        },
        KeyCode.A, VirtualButton.LEFT);


        // Mover personagem para a direita
>>>>>>> 9dc593f (add adjusts)
        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
<<<<<<< HEAD
        }, KeyCode.D, VirtualButton.RIGHT);
=======
        },
        KeyCode.D, VirtualButton.RIGHT);

>>>>>>> 9dc593f (add adjusts)

        //Pular com o personagem
        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jump();
            }
<<<<<<< HEAD
        }, KeyCode.W, VirtualButton.A);
=======
        },
        KeyCode.W, VirtualButton.A);

>>>>>>> 9dc593f (add adjusts)

        //Atirar penas com o personagem
        getInput().addAction(new UserAction("Disparar pena") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).shoot();
            }
<<<<<<< HEAD
        }, MouseButton.PRIMARY);
=======
        },
        MouseButton.PRIMARY);

>>>>>>> 9dc593f (add adjusts)

        getInput().addAction(new UserAction("Disparar água") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).dispararAgua();
            }
<<<<<<< HEAD
        }, MouseButton.SECONDARY);
=======
        },
        MouseButton.SECONDARY);

>>>>>>> 9dc593f (add adjusts)

        getInput().addAction(new UserAction("PAUSE") {
            @Override
            protected void onActionBegin() {

               // FXGL.play("2024-05-01_19-17-10_online-audio-converter.com.wav");
                getGameController().pauseEngine();
            }
<<<<<<< HEAD
        }, KeyCode.F);
=======
        },
        KeyCode.F);

>>>>>>> 9dc593f (add adjusts)

        getInput().addAction(new UserAction("RESUME") {
            @Override
            protected void onActionBegin() {
                getGameController().resumeEngine();
            }
<<<<<<< HEAD
        }, KeyCode.G);
=======
        },
        KeyCode.G);
>>>>>>> 9dc593f (add adjusts)


        //Configurações de entradas de dados para testar o Espalha Lixo
        getInput().addAction(new UserAction("Move espalha lixo para a esquerda!") {
            @Override
            protected void onActionBegin() {
                enemy.getComponent(EnemyComponent.class).moveParaEsquerda();
            }
<<<<<<< HEAD
        }, KeyCode.J);
=======
        },
        KeyCode.J);

>>>>>>> 9dc593f (add adjusts)

        getInput().addAction(new UserAction("Move espalha lixo para a direita!") {
            @Override
            protected void onActionBegin() {
                enemy.getComponent(EnemyComponent.class).moveParaDireita();
            }
<<<<<<< HEAD
        }, KeyCode.K);
        getInput().addAction(new UserAction("Disparar com Espalha Lixo") {
=======
        },
        KeyCode.K);


        getInput().addAction(new UserAction("Disparar com Espalha Lixo"){

>>>>>>> 9dc593f (add adjusts)
            @Override
            protected void onActionBegin() {
                Entity enemy = getGameWorld().getSingleton(EntityType.ENEMY);

                Optional<Entity> entidadeMaisProxima = getGameWorld().getClosestEntity(enemy, (e) -> {
                    return true;
                });

<<<<<<< HEAD

                enemy.getComponent(EnemyComponent.class).atirar(entidadeMaisProxima.get());
            }
        }, KeyCode.L);

=======
                enemy.getComponent(EnemyComponent.class).atirar(entidadeMaisProxima.get());
            }
        },
        KeyCode.L);
>>>>>>> 9dc593f (add adjusts)
    }


/*
 Método acionado antes do init para definir o som de fundo
*/
    @Override
    protected void onPreInit() {
        // Modificando volume inicial
<<<<<<< HEAD
        getSettings().setGlobalMusicVolume(0.8);

        // Definindo som de fundo
        loopBGM("backsound.wav");

=======
        getSettings().setGlobalMusicVolume(0.20);

        // Definindo som de fundo
        loopBGM("backsound.wav");
>>>>>>> 9dc593f (add adjusts)
    }

    /*
    Método para iniciar o jogo adicionando a classe de fábrica (factory), também é reposponsável por
    invocar o plano de fundo e jogador e configurar a tela para se vincular ao jogador
<<<<<<< HEAD
     */
    @Override
    protected void initGame() {
        FXGL.play("mudado.wav");
=======
    */
    @Override
    protected void initGame(){

        FXGL.play("detenha.wav");
>>>>>>> 9dc593f (add adjusts)

        /*
         Vinculando a classe de fábrica que é responsável por saber
         as configurações de criação de cada entidade
         e as suas características quando invocada no jogo
        */
        getGameWorld().addEntityFactory(new MainFactory());

        // Invocando plano de fundo
        spawn("background");
<<<<<<< HEAD
=======

>>>>>>> 9dc593f (add adjusts)
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
<<<<<<< HEAD
=======

>>>>>>> 9dc593f (add adjusts)
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
<<<<<<< HEAD
           System.out.println("Acertou objeto combustivel");
=======
>>>>>>> 9dc593f (add adjusts)
           objetoCombustivel.getComponent(ObjetoCombustivelComponent.class).tomaDano();
        });

        onCollisionBegin(EntityType.DISPARO_DE_AGUA_JOGADOR, EntityType.OBJETO_COMBUSTIVEL, (tiro, objetoCombustivel) -> {
            tiro.removeFromWorld();
            objetoCombustivel.getComponent(ObjetoCombustivelComponent.class).recuperarVida();
        });
    }


    // Método main padrão chamando as configurações da biblioteca FXGL
<<<<<<< HEAD
    public static void main(String[] args) {
        launch(args);
    }

=======
    public static void main(String[] args){
        launch(args);
    }
>>>>>>> 9dc593f (add adjusts)
}