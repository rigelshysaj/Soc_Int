-- Svuota la tabella chat_entity_message
DELETE FROM chat_entity_message;

-- Svuota la tabella event_entity_participants
DELETE FROM event_entity_participants;

-- Svuota la tabella feedback_entity
DELETE FROM feedback_entity;

-- Svuota la tabella chat_entity
DELETE FROM chat_entity;

-- Svuota la tabella event_entity
DELETE FROM event_entity;

-- Svuota la tabella user_entity_preferences
DELETE FROM user_entity_preferences;

-- Svuota la tabella user_entity
DELETE FROM user_entity;


-- Popolamento tabella user_entity
INSERT INTO user_entity (id, email, name) VALUES
  (1, 'user1@example.com', 'User 1'),
  (2, 'user2@example.com', 'User 2'),
  (3, 'user3@example.com', 'User 3');

-- Popolamento tabella event_entity
INSERT INTO event_entity (id, title, description, date, time, location, max_participants, deadline, latitude, longitude, host_id, category) VALUES
  (1, 'Evento 1', 'Descrizione evento 1', '2023-08-15', '15:00', 'Luogo evento 1', 10, '2023-08-14', 41.123, 12.345, 1, 'SPORT'),
  (2, 'Evento 2', 'Descrizione evento 2', '2023-08-20', '14:30', 'Luogo evento 2', 15, '2023-08-18', 42.678, 13.456, 2, 'MUSIC'),
  (3, 'Evento 3', 'Descrizione evento 3', '2023-08-25', '18:00', 'Luogo evento 3', 20, '2023-08-22', 40.987, 11.111, 1, 'FOOD');

-- Popolamento tabella chat_entity
INSERT INTO chat_entity (id, event_id, is_active) VALUES
  (1, 1, true),
  (2, 2, true),
  (3, 3, true);

-- Popolamento tabella chat_entity_message
INSERT INTO chat_entity_message (id, chat_id, message) VALUES
  (1, 1, 'Ciao a tutti!'),
  (2, 1, 'Come state?'),
  (3, 2, 'Primo messaggio!'),
  (4, 2, 'Chi si unisce?');

-- Popolamento tabella feedback_entity
INSERT INTO feedback_entity (id, event_id, comment, rating) VALUES
  (1, 1, 'Ottimo evento!', 5),
  (2, 2, 'Divertimento garantito!', 4),
  (3, 3, 'Buon cibo e compagnia!', 4);

-- Popolamento tabella user_entity_preferences
INSERT INTO user_entity_preferences (user_entity_id, preferences) VALUES
  (1, 'SPORT,MUSIC'),
  (2, 'MUSIC,FOOD'),
  (3, 'SPORT,FOOD');

-- Popolamento tabella event_entity_participants
INSERT INTO event_entity_participants (events_id, participants_id) VALUES
  (1, 1),
  (1, 2),
  (2, 2),
  (3, 1),
  (3, 3);