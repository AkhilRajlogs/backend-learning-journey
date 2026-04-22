-- Fetch all tasks
SELECT * FROM tasks;

-- Fetch only titles
SELECT title FROM tasks;

-- Insert new task
INSERT INTO tasks (title, completed)
VALUES ('Learn SQL properly', false);

-- Update task status
UPDATE tasks
SET completed = true
WHERE id = 1;

-- Delete a task
DELETE FROM tasks
WHERE id = 1;

-- Fetch completed tasks
SELECT * FROM tasks
WHERE completed = true;
  