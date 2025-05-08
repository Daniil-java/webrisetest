--liquibase formatted sql

--changeset DanielK:1

CREATE TABLE IF NOT EXISTS users (
                        id              SERIAL PRIMARY KEY,
                        email           TEXT UNIQUE NOT NULL,
                        firstName       TEXT,
                        lastName        TEXT,
                        updated         DATE,
                        created         DATE
);

CREATE TABLE IF NOT EXISTS services (
                        id              SERIAL PRIMARY KEY,
                        name            TEXT NOT NULL,
                        description     TEXT,
                        baseCost        DECIMAL,
                        durationDays    INT NOT NULL,
                        subscribeCount  INT,
                        updated         DATE,
                        created         DATE
);

CREATE TABLE IF NOT EXISTS subscriptions (
                        id              SERIAL PRIMARY KEY,
                        userId          INT NOT NULL,
                        serviceId       INT NOT NULL,
                        status          TEXT,
                        subscriptionStatus TEXT,
                        startDate       DATE,
                        endDate         DATE,
                        created         DATE,
                        CONSTRAINT fkuser FOREIGN KEY (userId) REFERENCES users(id),
                        CONSTRAINT fkservice FOREIGN KEY (serviceId) REFERENCES services(id)
);

-- Индексы
CREATE INDEX IF NOT EXISTS idx_subscriptions_user_id ON subscriptions(userId);
CREATE INDEX IF NOT EXISTS idx_subscriptions_service_id ON subscriptions(serviceId);
CREATE INDEX IF NOT EXISTS idx_services_name ON services(name);

-- Тестовые данные
INSERT INTO services (name, description, durationDays, baseCost, subscribeCount)
VALUES
    ('YT_PREMIUM', 'YouTube без рекламы и с фоновым воспроизведением', 30, 199.00, 1200),
    ('VK_MUSIC', 'Музыкальный сервис ВКонтакте с подборками и оффлайн-доступом', 30, 149.00, 950),
    ('YA_PLUS', 'Плюс от Яндекса: музыка, фильмы и кэшбэк', 30, 299.00, 1800),
    ('NETFLIX', 'Доступ к библиотеке фильмов и сериалов Netflix', 30, 599.00, 2100);
