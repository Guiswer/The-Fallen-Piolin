package org.example;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.views.ScrollingBackgroundView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static org.example.EntityType.*;

/*
    Método de fabricação para configurar cada entidade
    quando invocada (spawn)
 */

/*
 COMENTÁRIOS IMPORTANTES!

 EntityBuilder() = método responsável para criar a entidade

 bbox() = método para definir o tamanho da caixa de colisão (HitBox)  - não sei traduzir hit box ;-;

 .with(new PhysicsComponent()) = utilizado junto ao bbox() torna a entidade um componente físico
                        ou seja que pode sofre física de colisão e sua hit box começa a funcionar!

  build() = método para finalizar a criação da entidade no EntityBuilder()
 */
public class MainFactory implements EntityFactory {

    // Invocar plano de fundo
    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .at(0, 120)
                .view(new ScrollingBackgroundView(texture("background/forest.png").getImage(), getAppWidth(), getAppHeight()) )
                .zIndex(-1)
                .with(new IrremovableComponent())
                .build();
    }

    /* Invocar plataforma
     OBS: este método é apenas para definir física para o personagem poder colidir com as plataformas,
        não sendo ligado literalmente com as plataformas visuais do mapa!
     */
    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {

        //System.out.println(data.<Float>get("rotation"));

        return entityBuilder()
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),  data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    // Improvisando spawn como nome vazio, para evitar erros no momento de gerar a camada de objetos do mapa
    // ESTE MÉTODO DEVE SER REMOVIDO POSTERIORMENTE, QUANDO O MOTIVO DO ERROR FOR ENCONTRADO!
    @Spawns("")
    public Entity newPl(SpawnData data) {
        return entityBuilder()
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),  data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }


    // Invocar moeda
    // MÉTODO QUE TAMBÉM SERÁ REMOVIDO NO FUTURO QUANDO NÃO HOUVER NECESSIDADE DE UTILIZAÇÃO DO PRIMEIRO MAPA
    @Spawns("coin")
    public Entity newCoin(SpawnData data) {
        return entityBuilder()
                .type(COIN)
                .viewWithBBox(new Circle(data.<Integer>get("width") / 2 , Color.GOLD))
                .build();
    }

    /*
        Invoca a pena quando o jogador atirar com o personagem, configura a direção
        da pena, velocidade, onde ela aparecerá e remove a mesma quando
        sair da tela do jogador (campo de visão)
     */
    @Spawns("feather")
    public Entity newFeather(SpawnData data) {
        // Obtém a entidade do jogador para saber a posição de onde
        // será lançada a pena
        Entity player = getGameWorld().getSingleton(EntityType.PLAYER);

        // LINHAS QUE DEVEM SER TRADUZIDOS POSTERIOMENTE PARA MELHOR ENTENDIMENTO KKKKKKKK
        // PS: Foi mal geuntiiiii!
        double featheProjectileDirectionX = player.getCenter().getX();
        double featherOriginDirectionY = player.getCenter().getY() - 35;
        double featherOriginDirectionX = featheProjectileDirectionX;
        double changeableScaleFeatherYByPlayerDirectionX = 0.5;

        // Regra para obter a direção que o personagem está direcionado
        if (player.getScaleX() < 0) {
            featheProjectileDirectionX = -player.getCenter().getX();
            featherOriginDirectionX -= 80;
            changeableScaleFeatherYByPlayerDirectionX = -0.5;
        }

        // Direção do projetil
        Point2D direction = new Point2D(featheProjectileDirectionX, 0);

        return  entityBuilder()
                .at(featherOriginDirectionX, featherOriginDirectionY)
                .type(FEATHER)
                .viewWithBBox("normal_feather.png")
                .collidable()
                .with(new ProjectileComponent(direction, 1000))
                .with(new OffscreenCleanComponent())
                .scale(0.5, changeableScaleFeatherYByPlayerDirectionX)
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {

        // Obtendo um componente de física específico para o jogador
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(25,10), BoundingShape.box(15, 50)));

        // Evita que o jogador grude nas paredes
        physics.setFixtureDef(new FixtureDef().friction(0.0f));

        return FXGL.entityBuilder(data)
                .type(PLAYER)

                // PODE SER UTILIZADO DEPOIS PARA UM SEGUNDA HIT BOX REDONDA
                // SIGNIFICANDO A CABEÇA DO PERSONAGEM
                //.bbox(new HitBox(new Point2D(5,15), BoundingShape.circle(6)))

                //Piolin
                .bbox(new HitBox(new Point2D(25,10), BoundingShape.box(15, 50)))
                .with(physics)
                // Componente possivel de colisão
                .with(new CollidableComponent(true))
                // Componente irremovivel do jogo
                .with(new IrremovableComponent())


                /*
                 INJETA AQUI A CLASSE PLAYER (COMPONENTE) QUE É BREVEMENTE CONFIGURADA EM UMA CLASSE
                 SEPARADA POR TER DIVERSOS RECURSOS QUE AS DEMAIS ENTIDADE NÃO PRECISAM!
                 */
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
// Obtendo um componente de física específico para o jogador
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(25,10), BoundingShape.box(15, 50)));

        // Evita que o jogador grude nas paredes
        physics.setFixtureDef(new FixtureDef().friction(0.0f));

        return FXGL.entityBuilder(data)
                .type(ENEMY)
                //Piolin
                .bbox(new HitBox(new Point2D(25,10), BoundingShape.box(15, 50)))
                .with(physics)
                // Componente possivel de colisão
                .with(new CollidableComponent(true))

                .with(new EnemyComponent())
                .build();
    }
}
