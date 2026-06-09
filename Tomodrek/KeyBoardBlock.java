package Tomodrek;



import arc.input.KeyCode;
import mindustry.content.Bullets;
import mindustry.gen.Call;
import mindustry.ui.dialogs.BaseDialog;

import mindustry.gen.Building;
import mindustry.world.blocks.defense.turrets.LaserTurret;

import mindustry.logic.LReadable;
import mindustry.logic.LWritable;
import mindustry.world.blocks.defense.turrets.PowerTurret;


public class KeyBoardBlock extends LaserTurret {

    public KeyBoardBlock(String name) {
        super(name);
        this.update = true;
        this.configurable = true;
        health = 450;
        size = 1;
        itemCapacity = 30;
        this.hasPower = false;
        hasPower = false;
        this.shootType = Bullets.damageLightning;

    }

    public class KeyBoardBlockBuild extends PowerTurret.PowerTurretBuild implements mindustry.logic.LReadable, mindustry.logic.LWritable {
        private double lastSentKey = 0.0d;
        public double[] internalMemory = new double[64];
        @Override
        public void updateTile() {


            super.updateTile();
            if (mindustry.Vars.player == null || mindustry.Vars.player.unit() == null) return;
            if (this.isControlled() && this.unit.controller() == mindustry.Vars.player.unit().controller()) {
                internalMemory[0] = this.isShooting() ? 1.0d : 0.0d;
                internalMemory[1] = this.unit != null ? (double) this.unit.aimX() : 0.0d;
                internalMemory[2] = this.unit != null ? (double) this.unit.aimY() : 0.0d;

                // Считываем физическую клавиатуру ПК
                double currentPressedKey = 0.0d;
                if (arc.Core.input.keyDown(arc.input.KeyCode.w)) currentPressedKey = 51.0d;
                else if (arc.Core.input.keyDown(arc.input.KeyCode.a)) currentPressedKey = 29.0d;
                else if (arc.Core.input.keyDown(arc.input.KeyCode.s)) currentPressedKey = 47.0d;
                else if (arc.Core.input.keyDown(arc.input.KeyCode.d)) currentPressedKey = 32.0d;
                else if (arc.Core.input.keyDown(arc.input.KeyCode.space)) currentPressedKey = 62.0d;
                else if (arc.Core.input.keyDown(arc.input.KeyCode.e)) currentPressedKey = 37.0d;
                else if (arc.Core.input.keyDown(KeyCode.mouseLeft)) currentPressedKey = 0.1d;
                else if (arc.Core.input.keyDown(KeyCode.mouseRight)) currentPressedKey = 1.1d;
                else {

                    for (arc.input.KeyCode key : arc.input.KeyCode.values()) {

                        if (arc.Core.input.keyDown(key)) {
                            currentPressedKey = (double) key.name().hashCode();
                            break;
                        }
                    }
                }


                if (lastSentKey != currentPressedKey) {
                    // Пишем локально (для локального хоста Ubuntu)
                    this.internalMemory[3] = currentPressedKey;

                    // УНИВЕРСАЛЬНЫЙ СЕТЕВОЙ МОСТ:
                    // Кодируем число currentPressedKey в объект Point2 (координаты)
                    // Движок игры пропустит этот пакет через защиту лазерной турели на ЛЮБОМ сервере!
                    int packedCoords = arc.math.geom.Point2.pack((int)currentPressedKey, 0);
                    mindustry.gen.Call.tileConfig(mindustry.Vars.player, this, packedCoords);

                    lastSentKey = currentPressedKey;
                }
            }
        }

        /**
         * СЕРВЕРНЫЙ МЕТОД: Принимает упакованные координаты, вытаскивает из них
         * оригинальный код кнопки и записывает в память сервера.
         */
        @Override
        public void configure(Object value) {
            if (value instanceof Integer) {
                // Распаковываем координату X, в которой мы спрятали код клавиши
                int packed = (Integer) value;
                double keyCode = (double) arc.math.geom.Point2.x(packed);

                // Записываем в память сервера. Теперь процессор на хостинге всё увидит!
                this.internalMemory[3] = keyCode;
            } else if (value instanceof Double) {
                this.internalMemory[3] = (Double) value;
            } else {
                super.configure(value);
            }
        }
        @Override
        public float progress() {
            return 0f;
        }

        @Override
        protected void updateShooting() {

        }


        @Override
        public boolean shouldConsume() {
            return false;
        }

        @Override
        public boolean readable(mindustry.logic.LExecutor exec) {
            return this.isValid(); // ИСПРАВЛЕНО: тип изменен на boolean!
        }

        @Override
        public boolean writable(mindustry.logic.LExecutor exec) {
            return this.isValid(); // ИСПРАВЛЕНО: тип изменен на boolean!
        }

        // --- 2. МЕТОДЫ САМОГО ЧТЕНИЯ И ЗАПИСИ (ПРИНИМАЮТ LVar, ВОЗВРАЩАЕТ VOID) ---
        @Override
        public void read(mindustry.logic.LVar index, mindustry.logic.LVar result) {
            // .numval — это поле примитива double, пишем БЕЗ круглых скобок
            int idx = (int) index.num();

            if (idx >= 0 && idx < internalMemory.length) {
                result.setnum(internalMemory[idx]);
            } else {
                result.setnum(0.0d);
            }
        }

        @Override
        public void write(mindustry.logic.LVar index, mindustry.logic.LVar value) {
            int idx = (int) index.num();

            if (idx >= 0 && idx < internalMemory.length) {
                this.internalMemory[idx] = value.num();
            }
        }


        }


    }



            

         
            





