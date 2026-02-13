package com.master.practiceReact.seed;

import com.master.practiceReact.repository.SubjectRepository;
import com.master.practiceReact.repository.TopicRepository;
import com.master.practiceReact.models.Entity.Topic;
import com.master.practiceReact.models.Entity.Subject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicSeeder implements CommandLineRunner {

    private final TopicRepository topicRepository;
    private final SubjectRepository subjectRepository;

    public TopicSeeder(TopicRepository topicRepository, SubjectRepository subjectRepository) {
        this.topicRepository = topicRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (topicRepository.count() > 0) {
            return; // Already seeded
        }

        // Auto-create subjects if they do not exist
        Subject math = getOrCreateSubject("Math");
        Subject science = getOrCreateSubject("Science");
        Subject english = getOrCreateSubject("English");
        Subject social = getOrCreateSubject("Social Studies");
        Subject art = getOrCreateSubject("Art & Music");
        Subject pe = getOrCreateSubject("Physical Education");
        Subject tech = getOrCreateSubject("Technology");
        Subject life = getOrCreateSubject("Life Skills");

        int order = 1;

        List<Topic> topics = List.of(
                // Math
                new Topic(math, "Numbers & Counting", order++, "🔢", "K-2"),
                new Topic(math, "Addition & Subtraction", order++, "➕➖", "K-2"),
                new Topic(math, "Multiplication & Division", order++, "✖️➗", "2-4"),
                new Topic(math, "Fractions & Decimals", order++, "🍰", "3-6"),
                new Topic(math, "Geometry", order++, "📐", "3-6"),
                new Topic(math, "Measurement", order++, "📏", "1-6"),
                new Topic(math, "Time & Calendar", order++, "⏰", "K-6"),
                new Topic(math, "Money & Coins", order++, "💰", "1-6"),
                new Topic(math, "Patterns & Sequences", order++, "🔁", "K-6"),
                new Topic(math, "Problem Solving", order++, "🧩", "K-6"),

                // Science
                new Topic(science, "Plants & Animals", order++, "🌱", "K-6"),
                new Topic(science, "Human Body", order++, "🧍‍♂️", "1-6"),
                new Topic(science, "Senses", order++, "👂👀👃👄", "K-2"),
                new Topic(science, "Weather & Seasons", order++, "☀️🌧️❄️", "K-6"),
                new Topic(science, "Earth & Space", order++, "🌍🌙🪐", "3-6"),
                new Topic(science, "Water & States of Matter", order++, "💧", "K-6"),
                new Topic(science, "Simple Machines", order++, "⚙️", "3-6"),
                new Topic(science, "Environment & Recycling", order++, "♻️", "K-6"),
                new Topic(science, "Magnets & Electricity", order++, "🧲⚡", "3-6"),
                new Topic(science, "Life Cycles", order++, "🔄", "K-6"),

                // English / Language Arts
                new Topic(english, "Alphabet & Phonics", order++, "🔤", "K-2"),
                new Topic(english, "Reading Comprehension", order++, "📖", "K-6"),
                new Topic(english, "Writing Sentences & Paragraphs", order++, "✍️", "1-6"),
                new Topic(english, "Spelling & Vocabulary", order++, "📝", "1-6"),
                new Topic(english, "Grammar & Punctuation", order++, "📚", "2-6"),
                new Topic(english, "Storytelling & Creative Writing", order++, "🗣️", "K-6"),
                new Topic(english, "Poetry", order++, "🎵", "K-6"),
                new Topic(english, "Listening & Speaking Skills", order++, "👂🗨️", "K-6"),

                // Social Studies
                new Topic(social, "Community & Neighborhood", order++, "🏘️", "K-3"),
                new Topic(social, "Maps & Globes", order++, "🗺️", "K-6"),
                new Topic(social, "Families & Traditions", order++, "👪", "K-6"),
                new Topic(social, "History of Local Area", order++, "🏛️", "3-6"),
                new Topic(social, "Important Holidays & Events", order++, "🎉", "K-6"),
                new Topic(social, "Famous People in History", order++, "🧑‍🏫", "3-6"),
                new Topic(social, "Countries & Cultures", order++, "🌎", "K-6"),
                new Topic(social, "Rules & Responsibilities", order++, "⚖️", "K-6"),

                // Art & Music
                new Topic(art, "Drawing & Coloring", order++, "🎨", "K-6"),
                new Topic(art, "Painting & Crafts", order++, "🖌️", "K-6"),
                new Topic(art, "Shapes & Patterns in Art", order++, "🔷", "K-6"),
                new Topic(art, "Singing & Songs", order++, "🎤", "K-6"),
                new Topic(art, "Musical Instruments", order++, "🎹", "K-6"),
                new Topic(art, "Rhythm & Beat", order++, "🥁", "K-6"),
                new Topic(art, "Famous Artists & Composers", order++, "🖼️🎼", "3-6"),

                // Physical Education
                new Topic(pe, "Basic Exercises", order++, "🏃", "K-6"),
                new Topic(pe, "Running, Jumping, Throwing", order++, "🤾", "K-6"),
                new Topic(pe, "Team Games", order++, "⚽", "K-6"),
                new Topic(pe, "Balance & Coordination", order++, "🤸", "K-6"),
                new Topic(pe, "Safety & Healthy Habits", order++, "🩺", "K-6"),

                // Technology / STEM
                new Topic(tech, "Computer Basics", order++, "💻", "K-6"),
                new Topic(tech, "Typing & Keyboard Skills", order++, "⌨️", "K-6"),
                new Topic(tech, "Simple Coding Concepts", order++, "💡", "3-6"),
                new Topic(tech, "Robotics", order++, "🤖", "3-6"),
                new Topic(tech, "Problem Solving / Puzzles", order++, "🧩", "K-6"),

                // Life Skills
                new Topic(life, "Personal Hygiene", order++, "🧼", "K-6"),
                new Topic(life, "Manners & Etiquette", order++, "🙏", "K-6"),
                new Topic(life, "Sharing & Cooperation", order++, "🤝", "K-6"),
                new Topic(life, "Emotional Awareness", order++, "❤️", "K-6"),
                new Topic(life, "Safety Rules", order++, "🚦", "K-6"),
                new Topic(life, "Healthy Eating", order++, "🥗", "K-6")
        );

        topicRepository.saveAll(topics);
        System.out.println("✅ Topics seeded successfully with emojis!");
    }

    // Helper method: get subject by name or create it if missing
    private Subject getOrCreateSubject(String name) {
        return subjectRepository.findByName(name)
                .orElseGet(() -> subjectRepository.save(new Subject(name)));
    }
}
