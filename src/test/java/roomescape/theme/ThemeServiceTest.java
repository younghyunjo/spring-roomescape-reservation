package roomescape.theme;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThemeServiceTest {
    @Mock
    private ThemeRepository themeRepository;

    @InjectMocks
    private ThemeService themeService;

    class FakeRepository {
        private AtomicLong lastId = new AtomicLong(0L);
        private List<ThemeEntity> themeEntities = new ArrayList<>();

        public void add(ThemeEntity e) {
            themeEntities.add(e);
        }

        public List<ThemeEntity> list() {
            return themeEntities;
        }


        public void tearDown() {
            themeEntities.clear();
        }
    }
    private FakeRepository fakeRepository = new FakeRepository();


    @BeforeEach
    void setUp() {
        when(themeRepository.add(any())).thenAnswer((Answer<ThemeEntity>) invocation -> {
            Object[] args = invocation.getArguments();
            ThemeEntity answerEntity = (ThemeEntity) args[0];
            answerEntity.setId(fakeRepository.lastId.incrementAndGet());
            fakeRepository.add(answerEntity);
            return answerEntity;
        });

        when(themeRepository.themes()).thenReturn(fakeRepository.list());
    }

    @AfterEach
    void tearDown() {
        fakeRepository.tearDown();
    }

    @Test
    void givenThemeRequestWhenAddThenAdded() {
        //given
        ThemeRequest given = new ThemeRequest("name", "desc", "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg");

        //when
        Theme added = themeService.add(given);

        //then
        assertThat(added.getId()).isEqualTo(1L);
        assertThat(added.getName()).isEqualTo("name");
        assertThat(added.getDescription()).isEqualTo("desc");
        assertThat(added.getThumbnail()).isEqualTo("https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg");
    }

    @Test
    void givenThemeAddedWhenThemesThenAddedThemes() {
        //given
        ThemeRequest themeRequest1 = new ThemeRequest("name1", "desc1", "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg");
        ThemeRequest themeRequest2 = new ThemeRequest("name2", "desc2", "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d42.jpg");

        Theme theme1 = themeService.add(themeRequest1);
        Theme theme2 = themeService.add(themeRequest2);

        //when
        List<Theme> themes = themeService.themes();

        //then
        assertThat(themes.size()).isEqualTo(2);
        assertThat(themes).contains(theme1);
        assertThat(themes).contains(theme2);
    }
}