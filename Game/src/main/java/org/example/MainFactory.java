package org.example;

import com.almasb.fxgl.core.math.FXGLMath;
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
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static org.example.EntityType.*;

/*
<<<<<<< HEAD
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
=======
   Método de fabricação para configurar cada entidade
   quando invocada (spawn)

   COMENTÁRIOS IMPORTANTES!

   EntityBuilder() = método responsável para criar a entidade

   bbox() = método para definir o tamanho da caixa de colisão (HitBox)  - não sei traduzir hit box ;-;

  .with(new PhysicsComponent()) = utilizado junto ao bbox() torna a entidade um componente físico
                        ou seja que pode sofre física de colisão e sua hit box começa a funcionar!

  build() = método para finalizar a criação da entidade no EntityBuilder()
*/

>>>>>>> 9dc593f (add adjusts)
public class MainFactory implements EntityFactory {

    // Invocar plano de fundo
    @Spawns("background")
<<<<<<< HEAD
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .at(0, 120)
                .view(new ScrollingBackgroundView(texture("background/forest.png").getImage(), getAppWidth(), getAppHeight()) )
                .zIndex(-1)
=======
    public Entity newBackground(SpawnData data){

        return entityBuilder()
                .at(-10, 60)
                .view(new ScrollingBackgroundView(texture("background/forest.png").getImage(), getAppWidth()-500,
                 getAppHeight()) )
                .zIndex(-5)
>>>>>>> 9dc593f (add adjusts)
                .with(new IrremovableComponent())
                .build();
    }

<<<<<<< HEAD
    /* Invocar plataforma
     OBS: este método é apenas para definir física para o personagem poder colidir com as plataformas,
        não sendo ligado literalmente com as plataformas visuais do mapa!
     */
=======
    /*
        Invocar plataforma
        OBS: este método é apenas para definir física para o personagem poder colidir com as plataformas,
        não sendo ligado literalmente com as plataformas visuais do mapa!
     */

>>>>>>> 9dc593f (add adjusts)
    @Spawns("platform")
    public Entity newPlataforma(SpawnData data) {

        PhysicsComponent physics = new PhysicsComponent();
<<<<<<< HEAD
        // estes são objetos jbox2d diretos, então na verdade não introduzimos uma nova API
=======

        // Estes são objetos jbox2d diretos, então na verdade não introduzimos uma nova API
>>>>>>> 9dc593f (add adjusts)
        FixtureDef fd = new FixtureDef();
        fd.setRestitution(0);
        fd.setFriction(0);
        physics.setFixtureDef(fd);

        return entityBuilder()
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),  data.<Integer>get("height"))))
                .with(physics)
                .build();
    }
<<<<<<< HEAD
=======

>>>>>>> 9dc593f (add adjusts)
    @Spawns("platform-diagonal")
    public Entity newPlataformaDiagonal(SpawnData data) {

        return  entityBuilder()
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),  data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("objetoCombustivel")
    public Entity newObjetoCombustivel(SpawnData data) {

        //Configurando partícula
        ParticleEmitter emissorDeParticula = ParticleEmitters.newFireEmitter();

        emissorDeParticula.setMaxEmissions(Integer.MAX_VALUE);
        emissorDeParticula.setNumParticles(0);
        emissorDeParticula.setEmissionRate(1);
        emissorDeParticula.setSize(1, 1);
        emissorDeParticula.setScaleFunction(i -> FXGLMath.randomPoint2D().multiply(0.001));
        emissorDeParticula.setExpireFunction(i -> Duration.seconds(0.5));
        emissorDeParticula.setSpawnPointFunction(i -> new Point2D(5, 5));

<<<<<<< HEAD

=======
>>>>>>> 9dc593f (add adjusts)
        return  entityBuilder()
                .type(OBJETO_COMBUSTIVEL)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),  data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .with(new ObjetoCombustivelComponent())
                .with(new ParticleComponent(emissorDeParticula))
                .build();
    }

    @Spawns("parede_limite_do_mapa")
    public Entity newPlaatfawdorm(SpawnData data) {
        return entityBuilder()
                .type(PAREDE_INVISIVEL_LIMITE_DO_MAPA)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),  data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new CollidableComponent())
                .build();
    }


    @Spawns("poligono")
    public Entity newPlaawdatfawawdorm(SpawnData data) {
        Polygon poly = data.<Polygon>get("polygon");
        List<Double> points = poly.getPoints();

        return entityBuilder()
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.polygon(points.get(0),
                                points.get(1), points.get(2), points.get(3),
                            points.get(4), points.get(5))))
                .with(new PhysicsComponent())
                .build();
    }


    // Invocar moeda
    // MÉTODO QUE TAMBÉM SERÁ REMOVIDO NO FUTURO QUANDO NÃO HOUVER NECESSIDADE DE UTILIZAÇÃO DO PRIMEIRO MAPA
    @Spawns("coin")
    public Entity newCoin(SpawnData data) {
        return entityBuilder()
                .type(COIN)
                .zIndex(2)
                .viewWithBBox(new Circle(160/ 2 , Color.GOLD))
                .with( new PhysicsComponent())
                .build();
    }

<<<<<<< HEAD
    @Spawns("barra_de_vida_objeto_combustivel")
    public Entity newBarraDeVidaObjetosCombustiveis(SpawnData data) {

        System.out.println("awdawdawdwadwawd");

=======

    @Spawns("barra_de_vida_objeto_combustivel")
    public Entity newBarraDeVidaObjetosCombustiveis(SpawnData data) {

>>>>>>> 9dc593f (add adjusts)
       Entity e =  entityBuilder()
                .viewWithBBox(new Circle(30/ 2 , Color.GOLD))
                .with(new PhysicsComponent())
                .build();

       e.setReusable(true);

       return e;
    }

<<<<<<< HEAD

    /*
            Invoca a pena quando o jogador atirar com o personagem, configura a direção
            da pena, velocidade, onde ela aparecerá e remove a mesma quando
            sair da tela do jogador (campo de visão)
         */
    @Spawns("feather")
    public Entity newFeather(SpawnData data) {
=======
        /*
        Invoca a pena quando o jogador atirar com o personagem, configura a direção
        da pena, velocidade, onde ela aparecerá e remove a mesma quando
        sair da tela do jogador (campo de visão)
        */

    @Spawns("feather")
    public Entity newFeather(SpawnData data){

>>>>>>> 9dc593f (add adjusts)
        // Obtém a entidade do jogador para saber a posição de onde
        // será lançada a pena
        Entity player = getGameWorld().getSingleton(EntityType.JOGADOR);

        // LINHAS QUE DEVEM SER TRADUZIDOS POSTERIOMENTE PARA MELHOR ENTENDIMENTO KKKKKKKK
        // PS: Foi mal geuntiiiii!
        double direcaoDoProjetil = player.getCenter().getX();
        double origemDoProjetilEixoY = player.getCenter().getY() - 35;
        double origemDoProjetilEixoX = direcaoDoProjetil - 40;
        double mudaEscalaDaImagemParaDirecaoDoProjetil = 0.4;

        // Regra para disparo para a esquerda
        if (player.getScaleX() < 0) {
            origemDoProjetilEixoX = direcaoDoProjetil - 45;
            direcaoDoProjetil = -player.getCenter().getX();
            mudaEscalaDaImagemParaDirecaoDoProjetil = -0.4;
        }

        // Direção do projetil
        Point2D direction = new Point2D(direcaoDoProjetil, 0);

        return  entityBuilder()
                .at(origemDoProjetilEixoX, origemDoProjetilEixoY)
                .type(DISPARO_DE_PENA_JOGADOR)
                .viewWithBBox("normal_feather.png")
                .collidable()
                .with(new ProjectileComponent(direction, 1000))
                .with(new OffscreenCleanComponent())
                .scale(0.4, mudaEscalaDaImagemParaDirecaoDoProjetil)
                .build();
    }

    @Spawns("tiroDoEspalhaLixo")
    public Entity newTiroDoEspalhaLixo(SpawnData data) {
        // Obtém a entidade do jogador para saber a posição de onde
        // será lançada a pena
        Entity espalhaLixo = getGameWorld().getSingleton(EntityType.ENEMY);
        Entity player = getGameWorld().getSingleton(EntityType.JOGADOR);
        double playerPosicaoX = player.getPosition().getX();

        double direcaoDoProjetil = espalhaLixo.getCenter().getX();
        double origemDoProjetilEixoY = espalhaLixo.getCenter().getY() - 35;
        double origemDoProjetilEixoX = direcaoDoProjetil;
        double mudaEscalaDaImagemParaDirecaoDoProjetil = 0.5;


        // Regra para disparo para a esquerda
        if (espalhaLixo.getPosition().getX() > playerPosicaoX) {
            direcaoDoProjetil = -espalhaLixo.getCenter().getX();
            origemDoProjetilEixoX -= 80;
            mudaEscalaDaImagemParaDirecaoDoProjetil = -0.5;
            espalhaLixo.getComponent(EnemyComponent.class).moveParaEsquerda();
<<<<<<< HEAD
        } else {
            espalhaLixo.getComponent(EnemyComponent.class).moveParaDireita();
        }
=======
        }
        else {
            espalhaLixo.getComponent(EnemyComponent.class).moveParaDireita();
        }

>>>>>>> 9dc593f (add adjusts)
        espalhaLixo.getComponent(EnemyComponent.class).pararPersonagem();

        // Direção do projetil
        Point2D direction = new Point2D(direcaoDoProjetil, 0);

        return  entityBuilder()
                .at(origemDoProjetilEixoX, origemDoProjetilEixoY)
                .type(DISPARO_INIMIGO)
                .viewWithBBox("normal_feather.png")
                .collidable()
                .with(new ProjectileComponent(direction, 1000))
                .with(new OffscreenCleanComponent())
                .scale(0.5, mudaEscalaDaImagemParaDirecaoDoProjetil)
                .build();
    }


    @Spawns("disparo_de_agua")
<<<<<<< HEAD
    public Entity newDisparoDeAgua(SpawnData data) {
=======
    public Entity newDisparoDeAgua(SpawnData data){

>>>>>>> 9dc593f (add adjusts)
        // Obtém a entidade do jogador para saber a posição de onde
        // será lançada a pena
        Entity player = getGameWorld().getSingleton(EntityType.JOGADOR);

        double direcaoDoProjetil = player.getCenter().getX();
        double origemDoProjetilEixoY = player.getCenter().getY() - 80;
        double origemDoProjetilEixoX = direcaoDoProjetil - 160;
        double mudaEscalaDaImagemParaDirecaoDoProjetil = 0.1;

        // Regra para disparo para a esquerda
        if (player.getScaleX() < 0) {
            origemDoProjetilEixoX = direcaoDoProjetil - 160;
            direcaoDoProjetil = -player.getCenter().getX();
            mudaEscalaDaImagemParaDirecaoDoProjetil = -0.1;
        }

        // Direção do projetil
        Point2D direction = new Point2D(direcaoDoProjetil, 0);

        return  entityBuilder()
                .at(origemDoProjetilEixoX, origemDoProjetilEixoY)
                .type(DISPARO_DE_AGUA_JOGADOR)
                .viewWithBBox("waterD.png")
                .collidable()
                .with(new ProjectileComponent(direction, 1000))
                .with(new OffscreenCleanComponent())
                .scale(0.1, mudaEscalaDaImagemParaDirecaoDoProjetil)
                .build();
    }


    @Spawns("player")
    public Entity newPlayer(SpawnData data) {

        // Obtendo um componente de física específico para o jogador
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(25,10), BoundingShape.box(15, 50)));


        // estes são objetos jbox2d diretos, então na verdade não introduzimos uma nova API
        FixtureDef fd = new FixtureDef();
        fd.setRestitution(0);
<<<<<<< HEAD
        // Evita que o jogador grude nas paredes
        fd.setFriction(0);


        physics.setFixtureDef(fd);



=======

        // Evita que o jogador grude nas paredes
        fd.setFriction(0);

        physics.setFixtureDef(fd);


>>>>>>> 9dc593f (add adjusts)
        return FXGL.entityBuilder(data)
                .type(JOGADOR)

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


        // estes são objetos jbox2d diretos, então na verdade não introduzimos uma nova API
        FixtureDef fd = new FixtureDef();
        fd.setRestitution(0);

        // Evita que o jogador grude nas paredes
        fd.setFriction(0);

        physics.setFixtureDef(fd);

        return FXGL.entityBuilder(data)
                .type(ENEMY)
                //Piolin
                .bbox(new HitBox(new Point2D(25,10), BoundingShape.box(15, 50)))
                .with(physics)
                // Componente possivel de colisão
                .with(new CollidableComponent(true))

                //Sensor para verificar proximidade do PIOLIN
                .with(new SensorComponent())
                .with(new EnemyComponent())
                .build();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 9dc593f (add adjusts)
