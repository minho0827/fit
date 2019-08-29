package com.intellisyscorp.fitzme_android.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

// TODO(sjkim): needs EqualsAndHashCode annotation?
@EqualsAndHashCode(callSuper = false)
@Data
public class ConfigVO {
    @SerializedName("public")
    public PublicData public_;

    @SerializedName("private")
    public PrivateData private_;

    @EqualsAndHashCode(callSuper = false)
    @Data
    public class PublicData {
        List<PartData> part;
        List<CategoryData> category;
        List<TagData> tag;
        List<ColorData> color;
        List<WeatherData> weather;
        List<GenderData> gender;
        List<AgegroupData> agegroup;

        @EqualsAndHashCode(callSuper = false)
        @Data
        public class PartData {
            private String name_en;
            private String name_ko;
        }

        @EqualsAndHashCode(callSuper = false)
        @Data
        public class CategoryData {
            private String name_en;
            private String name_ko;
            private String part;
        }

        @EqualsAndHashCode(callSuper = false)
        @Data
        public class TagData {
            private String name;
            private String name_ko;
        }

        @EqualsAndHashCode(callSuper = false)
        @Data
        public class ColorData {
            private String name;
            private String name_ko;
            private String rgb;
        }

        @EqualsAndHashCode(callSuper = false)
        @Data
        public class WeatherData {
            private String name;
            private String name_ko;
        }

        @EqualsAndHashCode(callSuper = false)
        @Data
        public class GenderData {
            private String name;
            private String name_ko;
            // FIXME(sjkim): need?
            private boolean isSelected;
        }

        @EqualsAndHashCode(callSuper = false)
        @Data
        public class AgegroupData {
            private String name;
            private String name_ko;
            // FIXME(sjkim): need?
            private boolean isSelected;
        }
    }

    @EqualsAndHashCode(callSuper = false)
    @Data
    public class PrivateData {
    }
}
