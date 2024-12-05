package by.javaguru.experienceservice.features.experience;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExperienceAggregatorModelTest {

   @Test
   void shouldDecrementCounterWhenSetNewExperience() {
      ExperienceAggregatorModel experienceAggregatorModel = new ExperienceAggregatorModel();
      assertEquals(2, experienceAggregatorModel.getCounter().get());
      experienceAggregatorModel.setExperience(new Experience());
      assertEquals(1, experienceAggregatorModel.getCounter().get());
   }

   @Test
   void shouldNotChangeCounterWhenChangeExperienceFromNullToNull() {
      ExperienceAggregatorModel experienceAggregatorModel = new ExperienceAggregatorModel();
      assertEquals(2, experienceAggregatorModel.getCounter().get());
      experienceAggregatorModel.setExperience(null);
      assertEquals(2, experienceAggregatorModel.getCounter().get());
   }

   @Test
   void shouldNotChangeCounterWhenChangeExperienceFromObjToSameObj() {
      ExperienceAggregatorModel experienceAggregatorModel = new ExperienceAggregatorModel();
      assertEquals(2, experienceAggregatorModel.getCounter().get());

      Experience experience = new Experience();
      experienceAggregatorModel.setExperience(experience);
      assertEquals(1, experienceAggregatorModel.getCounter().get());
      experienceAggregatorModel.setExperience(experience);
      assertEquals(1, experienceAggregatorModel.getCounter().get());
   }

   @Test
   void shouldNotChangeCounterWhenChangeExperienceFromObjToSimilarObj() {
      ExperienceAggregatorModel experienceAggregatorModel = new ExperienceAggregatorModel();
      assertEquals(2, experienceAggregatorModel.getCounter().get());

      Experience experience1 = new Experience();
      experience1.setId(1L);
      Experience experience2 = new Experience();
      experience2.setId(2L);
      experienceAggregatorModel.setExperience(experience1);
      assertEquals(1, experienceAggregatorModel.getCounter().get());
      experienceAggregatorModel.setExperience(experience2);
      assertEquals(1, experienceAggregatorModel.getCounter().get());
   }

   @Test
   void shouldNotChangeCounterWhenChangeExperienceFromObjToOtherObj() {
      ExperienceAggregatorModel experienceAggregatorModel = new ExperienceAggregatorModel();
      assertEquals(2, experienceAggregatorModel.getCounter().get());

      Experience experience1 = new Experience();
      Experience experience2 = new Experience();
      experience2.setLink("sdsds");
      experienceAggregatorModel.setExperience(experience1);
      assertEquals(1, experienceAggregatorModel.getCounter().get());
      experienceAggregatorModel.setExperience(experience2);
      assertEquals(1, experienceAggregatorModel.getCounter().get());
   }

}