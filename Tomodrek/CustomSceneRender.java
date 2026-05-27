package Tomodrek;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.input.KeyCode;
import arc.math.*;
import arc.math.geom.*;
import arc.util.Time;
import mindustry.Vars;

public class CustomSceneRender {
    // Базовые координаты объекта (смещение относительно центра/игрока)
    public float objectX = 0f;
    public float objectY = 0f;
    public float objectZ = 0f;
    public float objectScale = 1.2f;
    public float camX = 0f;     // Влево (-) / Вправо (+)
    public float camY = 0f;     // Вниз (-) / Вверх (+) — высота камеры
    public float camZ = 15f;     // Расстояние до куба (Приближение/Отдаление)
    public float fov = 700f;
    // Переменные анимации и вращения
    private float rotationAngleX = 0f;
    private float rotationAngleY = 0f;
    private float animTime = 0f;

    // Массивы для 8 вершин куба
    private final float[] screenX = new float[8];
    private final float[] screenY = new float[8];
    private final boolean[] visible = new boolean[8];

    private TextureRegion texture;
    private final Face[] faces = new Face[6];

    public CustomSceneRender() {
        // Откалиброванная сетка обхода вершин для всех 6 граней куба
        faces[0] = new Face(0, 3, 2, 1); // Задняя
        faces[1] = new Face(4, 5, 6, 7); // Передняя
        faces[2] = new Face(0, 1, 5, 4); // Нижняя
        faces[3] = new Face(3, 7, 6, 2); // Верхняя
        faces[4] = new Face(0, 4, 7, 3); // Левая
        faces[5] = new Face(1, 2, 6, 5); // Правая
    }

    public void render() {
        if(texture == null) texture = Core.atlas.find("logic-processor");

        // Управление смещением объекта кнопками со стрелочками (← ↑ → ↓)
        float controlSpeed = 4f * Time.delta / 60f;
       // if(Core.input.keyDown(arc.input.KeyCode.w))    objectY += controlSpeed;
      //  if(Core.input.keyDown(arc.input.KeyCode.s))  objectY -= controlSpeed;
      //  if(Core.input.keyDown(arc.input.KeyCode.a))  objectX -= controlSpeed;
      //  if(Core.input.keyDown(arc.input.KeyCode.d)) objectX += controlSpeed;

        // Вращение и синусоида покачивания
        rotationAngleY += 30f * Time.delta / 60f;
        rotationAngleX += 15f * Time.delta / 60f;
        animTime += Time.delta / 60f;

        // Итоговый анимированный Y (внешнее управление + левитация)
        float finalY = objectY + Mathf.sin(animTime * 3f, 0.2f);

        float screenW = Core.graphics.getWidth();
        float screenH = Core.graphics.getHeight();

        // Центр экрана (всегда привязан к игроку во время матча)
        float centerX = screenW / 2f;
        float centerY = screenH / 2f;

        // Фиксируем 2D-матрицу экрана
        Draw.flush();
        Draw.proj(0, 0, screenW, screenH);

        // Геометрия куба (8 углов)
        float[][] cubeVertices = {
                {-0.5f * objectScale, -0.5f * objectScale, -0.5f * objectScale},
                { 0.5f * objectScale, -0.5f * objectScale, -0.5f * objectScale},
                { 0.5f * objectScale,  0.5f * objectScale, -0.5f * objectScale},
                {-0.5f * objectScale,  0.5f * objectScale, -0.5f * objectScale},
                {-0.5f * objectScale, -0.5f * objectScale,  0.5f * objectScale},
                { 0.5f * objectScale, -0.5f * objectScale,  0.5f * objectScale},
                { 0.5f * objectScale,  0.5f * objectScale,  0.5f * objectScale},
                {-0.5f * objectScale,  0.5f * objectScale,  0.5f * objectScale}
        };

        float radX = rotationAngleX * Mathf.degRad, radY = rotationAngleY * Mathf.degRad;
        float cosX = Mathf.cos(radX), sinX = Mathf.sin(radX);
        float cosY = Mathf.cos(radY), sinY = Mathf.sin(radY);
            
        float[] worldZ = new float[8];

        for (int i = 0; i < 8; i++) {
            float x = cubeVertices[i][0];
            float y = cubeVertices[i][1];
            float z = cubeVertices[i][2];

            float rotY1 = y * cosX - z * sinX;
            float rotZ1 = y * sinX + z * cosX;
            float rotX2 = x * cosY - rotZ1 * sinY;
            float rotZ2 = x * sinY + rotZ1 * cosY;

            // Рендерим куб в виртуальных 3D координатах
            float viewX = (rotX2 + objectX);
            float viewY = (rotY1 + finalY);
            float viewZ = (rotZ2 + objectZ) - 5f;

            worldZ[i] = viewZ;

            if (viewZ >= -0.1f) {
                visible[i] = false;
                continue;
            }


            screenX[i] = centerX + (viewX / -viewZ) * fov;
            screenY[i] = centerY + (viewY / -viewZ) * fov;
            visible[i] = true;
        }

        for(Face face : faces) {
            face.avgZ = (worldZ[face.p1] + worldZ[face.p2] + worldZ[face.p3] + worldZ[face.p4]) / 4f;
        }

        java.util.Arrays.sort(faces, (f1, f2) -> Float.compare(f1.avgZ, f2.avgZ));

        Draw.color(Color.white);
        for(Face face : faces) {
            drawFace(face.p1, face.p2, face.p3, face.p4);
        }

        // Корректно возвращаем матрицу 2D камеры мира Mindustry
       //
        // Draw.flush();
       Draw.proj(Core.camera.mat);
    }

    private void drawFace(int p1, int p2, int p3, int p4) {
        if (!visible[p1] || !visible[p2] || !visible[p3] || !visible[p4]) return;

        float edge1X = screenX[p2] - screenX[p1];
        float edge1Y = screenY[p2] - screenY[p1];
        float edge2X = screenX[p3] - screenX[p1];
        float edge2Y = screenY[p3] - screenY[p1];
        if ((edge1X * edge2Y - edge1Y * edge2X) <= 0) return;

        if (texture != null) {
            Fill.quad(texture, screenX[p1], screenY[p1], screenX[p2], screenY[p2], screenX[p3], screenY[p3], screenX[p4], screenY[p4]);
        }
    }

    private static class Face {
        int p1, p2, p3, p4;
        float avgZ;
        Face(int p1, int p2, int p3, int p4) { this.p1 = p1; this.p2 = p2; this.p3 = p3; this.p4 = p4; }
    }
}