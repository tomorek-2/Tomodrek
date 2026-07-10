"Этот плагин "Tomodrek" имеет функции администратирования, ограничение не для администрации зоны строительства и меню для администрации с выбором наказания. Всё хранится в Momodrek001, он включает три класса: сам Momodrek001, AdminChecker и ShadowBanHashMap. Включает теневой бан. Чтобы включить модератора в систему, создайте папку откуда вы запускаете сервер /config/config, оттуда будет искать плагин uuid и IP модераторов. Структуру смотрите ниже. У ShadowBanHashMap есть проблема: нет метода на удаление из ОЗУ HashMap (очень производительный способ хранение коллекций) кроме выключения хоста. ЧТобы сохранить нарушителей надо нажать на кнопку в amenu (смотрите в коде). Если вы хост и вам нужен плагин, то напишите в Telegram чтобы я помог с кодом."
Также плагин имеет гибкую настройку параметров. Я не знаю английский, поэтому переменные ломанные. Смотрите ниже пример.
# Важно
"Это мой первый плагин и первая документация."
# Дополнительно:
Admins.json: "
{
"rootAdmins": [
"uuid-root-2"
],
"admins": [
"uuid-admin-1",
"uuid-admin-2"
],
"moders": [
"uuid-moder-1",
"uuid-moder-2"
],
"reserve": [
"uuid-reserve-1",
"uuid-reserve-2"
],
"ip": [
"192.168.0.103",
"8.8.8.8"
]
}"
playerInShadowBan.json (был декомпилирован): "{
shadowBan: [
<Здесь uuid нарушителей>
]
}"
ConfigObject.json:
"{
"IntervalEnabledOffAllBlocksMalis": 60,
"IntervalSendInChatAgit": 499,
"LimitXStartAction": 0,
"LimitXEndAction": 10000,
"LimitYStartAction": 0,
"LimitYEndAction": 10000,
"URLCommandTelegramChannelServer": "https://t.me",
"URLCommandChat": "https://t.me",
"IPerekir": "127.0.0.1",
"PortErekir": 1082,
"IPSerpulo": "127.0.0.1",
"PortSerpulo": 1081,
"LocalRu1Agit": "Здесь очень топорная локализация через switch и if. - /tg",
"LocalRu2Agit": "amenu нельзя настроить. - /tg",
"LocalRu3Agit": "Также, aboutPlugin нельзя изменить через конфигурации. - /tg",
"LocalRu4Agit": "Почему я не перевёл? Так надо. - /tg",
"LocalRu5Agit": " Не забудьте подставить свой текст. Не ставьте у агитации 0, вам придётся убивать процесс через tty, вам это надо?- /tg",
"LocalEn1Agit": " - /tg",
"LocalEn2Agit": " - /tg",
"LocalEn3Agit": "- /tg",
"LocalEn4Agit": ".  - /tg",
"LocalEn5Agit": "K= - /tg"
}"