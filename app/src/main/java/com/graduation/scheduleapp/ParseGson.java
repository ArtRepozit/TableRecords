package com.graduation.scheduleapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ParseGson {

    public class DayNumber{
        @SerializedName("1")
        private List<LessonNumber> monday;
        @SerializedName("2")
        private List<LessonNumber> tuesday;
        @SerializedName("3")
        private List<LessonNumber> wednesday;
        @SerializedName("4")
        private List<LessonNumber> thursday;
        @SerializedName("5")
        private List<LessonNumber> friday;
        @SerializedName("6")
        private List<LessonNumber> saturday;

        public List<LessonNumber> getMonday() {
            return monday;
        }

        public List<LessonNumber> getTuesday() {
            return tuesday;
        }

        public List<LessonNumber> getWednesday() {
            return wednesday;
        }

        public List<LessonNumber> getThursday() {
            return thursday;
        }

        public List<LessonNumber> getFriday() {
            return friday;
        }

        public List<LessonNumber> getSaturday() {
            return saturday;
        }
    }

                    public class LessonNumber{
                        @SerializedName("1")
                        private List<LessonInfo> lessonOne;
                        @SerializedName("2")
                        private List<LessonInfo> lessonTwo;
                        @SerializedName("3")
                        private List<LessonInfo> lessonThree;
                        @SerializedName("4")
                        private List<LessonInfo> lessonFour;
                        @SerializedName("5")
                        private List<LessonInfo> lessonFive;
                        @SerializedName("6")
                        private List<LessonInfo> lessonSix;
                        @SerializedName("7")
                        private List<LessonInfo> lessonSeven;

                        public List<LessonInfo> getLessonOne() {
                            return lessonOne;
                        }

                        public List<LessonInfo> getLessonTwo() {
                            return lessonTwo;
                        }

                        public List<LessonInfo> getLessonThree() {
                            return lessonThree;
                        }

                        public List<LessonInfo> getLessonFour() {
                            return lessonFour;
                        }

                        public List<LessonInfo> getLessonFive() {
                            return lessonFive;
                        }

                        public List<LessonInfo> getLessonSix() {
                            return lessonSix;
                        }

                        public List<LessonInfo> getLessonSeven() {
                            return lessonSeven;
                        }
                    }

                            public class LessonInfo {
                                @SerializedName("subject")
                                private String subject;
                                @SerializedName("teacher") //
                                private String teacherName;
                                @SerializedName("date_from")
                                private String dateOfLessonStart;
                                @SerializedName("date_to")
                                private String dateOfLessonEnd;
                                @SerializedName("auditories")
                                private List<Auditoria> auditories;
                                @SerializedName("type")
                                private String type ;
                                @SerializedName("week")
                                private String week;


                            }

                                    class Auditoria{
                                        @SerializedName("title")
                                        String title;
                                        @SerializedName("title")
                                        String color;
                                    }
}
