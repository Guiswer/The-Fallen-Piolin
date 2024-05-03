package org.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

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

// método de configurações usado para
// definir o tamanho da tela entre outros.
    @Override
    protected void initSettings(GameSettings settings) {
        // Configura tamanho da tela
        settings.setWidth(15*70); // 1050
        settings.setHeight(10*70); // 700
        System.out.println(settings.getMenuKey());
        // Habilita os menus de configurações dentro do jogo
        settings.setGameMenuEnabled(true);
        settings.setDeveloperMenuEnabled(true);

        // Inicia a cena de carregamento
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public LoadingScene newLoadingScene() {
                return new MainLoadingScene();
            }
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
        getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).shoot();
            }
        }, MouseButton.PRIMARY);

        getInput().addAction(new UserAction("SOM DETENHA O ESPALHA LIXO!") {
            @Override
            protected void onActionBegin() {
                System.out.println("ACAO AUDIO!");
                FXGL.play("2024-05-01_19-17-10_online-audio-converter.com.wav");
            }
        }, KeyCode.F);

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
    }


/*
 Método acionado antes do init para definir o som de fundo
 */
    @Override
    protected void onPreInit() {
        // Modificando volume inicial
        getSettings().setGlobalMusicVolume(0.01);
        // Definindo som de fundo
        loopBGM("you_won_the_battle_but_not_the_war.wav");
    }


    /*
    Método para iniciar o jogo adicionando a classe de fábrica (factory), também é reposponsável por
    invocar o plano de fundo e jogador e configurar a tela para se vincular ao jogador
     */
    @Override
    protected void initGame() {
        /*
         Vinculando a classe de fábrica que é responsável por saber
         as configurações de criação de cada entidade
         e as suas características quando invocada no jogo
         */
        getGameWorld().addEntityFactory(new MainFactory());

        player = null;

        // IGNORAR ESTES COMENTARIOS POR ENQUANTO....
        // READ MORE...
        //if (player != null) {
        //    player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(50, 50));
        //    player.setZIndex(Integer.MAX_VALUE);
        //

        // Invocando plano de fundo
        spawn("background");
        // Definindo o mapa
        setLevelFromMap("tmx/map-remastered777.tmx");

        //Invocando jogador
        player = spawn("player", 0, 600);

        enemy = spawn("enemy", 0, 600);


        Image image = new Image("assets/textures/life-bar.png");

        // Cria uma ImageView com a imagem carregada
        ImageView imageView = new ImageView(image);

        // Define algumas propriedades da ImageView, se necessário
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);


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
        onCollisionBegin(EntityType.FEATHER, EntityType.ENEMY, (bullet,a) -> {
            bullet.removeFromWorld();
            enemy.getComponent(EnemyComponent.class).tomaDano();
        });
    }


    // Método main padrão chamando as configurações da biblioteca FXGL
    public static void main(String[] args) {
        launch(args);
    }

}