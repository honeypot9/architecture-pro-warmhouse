# Project_template

Это шаблон для решения проектной работы. Структура этого файла повторяет структуру заданий. Заполняйте его по мере работы над решением.

# Задание 1. Анализ и планирование

### 1. Описание функциональности монолитного приложения

**Управление отоплением:**

- Пользователи могут включать/выключать отопление
- Система поддерживает дистанционное управление отоплением

**Мониторинг температуры:**

- Пользователи могут проверять температуру
- Система поддерживает дистанционную проверку температуры дома

### 2. Анализ архитектуры монолитного приложения

Язык программирования: Go
База данных: PostgreSQL
Архитектура: Монолитная, все компоненты системы (обработка запросов, бизнес-логика, работа с данными) находятся в рамках одного приложения.
Взаимодействие: Синхронное, запросы обрабатываются последовательно.
Масштабируемость: Ограничена, так как монолит сложно масштабировать по частям.
Развертывание: Требует остановки всего приложения.

### 3. Определение доменов и границы контекстов

1 Домен управления отоплением
1.1 поддомен управления датчиками
1.1.1 контекст подключения датчика
1.1.2 контекст отключение/включения датчика
1.2 поддомен мониторинга
1.3 поддомен управления пользователями
1.3.1 контекст журналирования пользователей

### **4. Проблемы монолитного решения**

1. все компоненты системы находятся в рамках одного приложения
2. массштабируемость ограничена, так как монолит сложно масштабировать по частям.
3. требует остановки всего приложения

### 5. Визуализация контекста системы — диаграмма С4

```markdown
[Визуализиция контекста системы](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_5.uml)
```

# Задание 2. Проектирование микросервисной архитектуры

В этом задании вам нужно предоставить только диаграммы в модели C4. Мы не просим вас отдельно описывать получившиеся микросервисы и то, как вы определили взаимодействия между компонентами To-Be системы. Если вы правильно подготовите диаграммы C4, они и так это покажут.

**Диаграмма контейнеров (Containers)**

```markdown
[Визуализиция контекста системы](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_2_containers.uml)
```

**Диаграмма компонентов (Components)**

```markdown
[Визуализиция контекста домена_управления_устройствами](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_2_device_domain_components.uml)
[Визуализиция контекста домена_управления_сценариями](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_2_device_scenario_domain_components.uml)
[Визуализиция контекста домена_управления_интеграцией](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_2_integration_domain_components.uml)
[Визуализиция контекста домена_управления_телеметрии](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_2_telemetry_domain_components.uml)
[Визуализиция контекста домена_управления_пользователями](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_2_user_domain_components.uml)
```

**Диаграмма кода (Code)**

```markdown
[Диаграмма кода](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_2_device_registry_code.uml)
```

# Задание 3. Разработка ER-диаграммы

```markdown
[er диаграмма](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_3_er_diagram.uml)
```

# Задание 4. Создание и документирование API

### 1. Тип API

Для реализации API планирую использовать REST APi так как:
- единый интерфейс
- нехависимость от технологии
- уменьшает нагрузку на сервер и ускоряет ответы

### 2. Документация API

[Api домена_управления_устройствами](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_4_device_domain_components_api.yml)
[Api домена_управления_сценариями](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_4_device_scenario_domain_components_api.yml)
[Api домена_управления_интеграцией](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_4_integration_domain_components_api.yml)
[Api домена_управления_телеметрии](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_4_telemetry_domain_components_api.yml)
[Api домена_управления_пользователями](https://github.com/honeypot9/architecture-pro-warmhouse/tree/warmhouse/schemas/sprint_1_4_user_domain_components_api.yml)

# Задание 5. Работа с docker и docker-compose

Перейдите в apps.

Там находится приложение-монолит для работы с датчиками температуры. В README.md описано как запустить решение.

Вам нужно:

1) сделать простое приложение temperature-api на любом удобном для вас языке программирования, которое при запросе /temperature?location= будет отдавать рандомное значение температуры.

Locations - название комнаты, sensorId - идентификатор названия комнаты

```
	// If no location is provided, use a default based on sensor ID
	if location == "" {
		switch sensorID {
		case "1":
			location = "Living Room"
		case "2":
			location = "Bedroom"
		case "3":
			location = "Kitchen"
		default:
			location = "Unknown"
		}
	}

	// If no sensor ID is provided, generate one based on location
	if sensorID == "" {
		switch location {
		case "Living Room":
			sensorID = "1"
		case "Bedroom":
			sensorID = "2"
		case "Kitchen":
			sensorID = "3"
		default:
			sensorID = "0"
		}
	}
```

2) Приложение следует упаковать в Docker и добавить в docker-compose. Порт по умолчанию должен быть 8081

3) Кроме того для smart_home приложения требуется база данных - добавьте в docker-compose файл настройки для запуска postgres с указанием скрипта инициализации ./smart_home/init.sql

Для проверки можно использовать Postman коллекцию smarthome-api.postman_collection.json и вызвать:

- Create Sensor
- Get All Sensors

Должно при каждом вызове отображаться разное значение температуры

Ревьюер будет проверять точно так же.


# **Задание 6. Разработка MVP**

Необходимо создать новые микросервисы и обеспечить их интеграции с существующим монолитом для плавного перехода к микросервисной архитектуре.

### **Что нужно сделать**

1. Создайте новые микросервисы для управления телеметрией и устройствами (с простейшей логикой), которые будут интегрированы с существующим монолитным приложением. Каждый микросервис на своем ООП языке.
2. Обеспечьте взаимодействие между микросервисами и монолитом (при желании с помощью брокера сообщений), чтобы постепенно перенести функциональность из монолита в микросервисы.

В результате у вас должны быть созданы Dockerfiles и docker-compose для запуска микросервисов. 