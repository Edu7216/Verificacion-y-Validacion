package es.unizar.eina.notepadv3;

import android.database.Cursor;

import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.unizar.eina.catlist.CatList;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.AllOf.allOf;

public class Notepadv3EspressoTest {
    private static final String TEST_NAME="Espresso";
    private static List<String> notas = new ArrayList<>();
    private static List<String> categorias = new ArrayList<>();



    @Rule
    public ActivityTestRule<Notepadv3> mActivityRule = new ActivityTestRule<>(Notepadv3.class);

    private void crearNota(String title, String body) {
        openActionBarOverflowOrOptionsMenu(mActivityRule.getActivity());
        //onView(withText(R.string.menu_insert)).check(matches(notNullValue()));
        onView(withText(R.string.menu_insert)).perform(click());

        // Se asegura de que la actividad actual es NoteEdit
        onView(withId(R.id.title)).check(matches(notNullValue()));

        // En el título inserta "Espresso Note Title <i>"
        onView(withId(R.id.title)).perform(typeText(title), closeSoftKeyboard());

        // En el cuerpo inserta "Espresso Note Body <i>"
        onView(withId(R.id.body)).perform(typeText(body), closeSoftKeyboard());

        // Confirma y vuelve a la actividad anterior
        onView(withId(R.id.confirm)).perform(click());

        notas.add(title);

        // Aserción: comprobación de que la nota está en la base de datos
        Cursor noteCursor = mActivityRule.getActivity().getmDbHelper().fetchNote(title);
        assertNoteEquals(title, body, noteCursor);

    }

    private void editarNota(String title, String body) {
        onView(withText(title)).perform(longClick());
        onView(withText(R.string.menu_edit)).perform(click());

        // Se asegura de que la actividad actual es NoteEdit

        // En el cuerpo inserta "Espresso Note Body <i>"
        onView(withId(R.id.body)).perform(replaceText(body), closeSoftKeyboard());

        // Confirma y vuelve a la actividad anterior
        onView(withId(R.id.confirm)).perform(click());

        // Aserción: comprobación de que la nota está en la base de datos
        Cursor noteCursor = mActivityRule.getActivity().getmDbHelper().fetchNote(title);
        assertNoteEquals(title, body, noteCursor);

    }

    private void editarCategoria(String name, String new_name) {

        boolean goBack = false;

        try {
            onView(withId(R.id.title)).check(matches(isDisplayed()));
        } catch (Exception e) {
            onView(withId(R.id.notepad_buttonCat)).perform(click());
            goBack = true;
        }

        onView(withText(name)).perform(longClick());
        onView(withText(R.string.menu_cat_edit)).perform(click());

        // Se asegura de que la actividad actual es NoteEdit
        onView(withId(R.id.cat_name)).check(matches(notNullValue()));

        // En el título inserta "Espresso Note Title <i>"
        onView(withId(R.id.cat_name)).perform(replaceText(new_name), closeSoftKeyboard());


        // Confirma y vuelve a la actividad anterior
        onView(withId(R.id.cat_confirm)).perform(click());

        categorias.remove(name);

        categorias.add(new_name);

        // Aserción: comprobación de que la nota se visualiza en el listado
        onView(withText(new_name)).check(matches(isDisplayed()));

        if(goBack) {
            goBack();
        }

    }

    private void crearCategoria(String name) {

        boolean goBack = false;

        try {
            onView(withId(R.id.title)).check(matches(isDisplayed()));
        } catch (Exception e) {
            onView(withId(R.id.notepad_buttonCat)).perform(click());
            goBack = true;
        }

        openActionBarOverflowOrOptionsMenu(mActivityRule.getActivity());
        onView(withText(R.string.menu_insert)).check(matches(notNullValue()));
        onView(withText(R.string.menu_insert)).perform(click());

        // Se asegura de que la actividad actual es NoteEdit
        onView(withId(R.id.cat_name)).check(matches(notNullValue()));

        // En el título inserta "Espresso Note Title <i>"
        onView(withId(R.id.cat_name)).perform(typeText(name), closeSoftKeyboard());


        // Confirma y vuelve a la actividad anterior
        onView(withId(R.id.cat_confirm)).perform(click());

        categorias.add(name);

        // Aserción: comprobación de que la nota se visualiza en el listado
        onView(withText(name)).check(matches(isDisplayed()));

        if(goBack) {
            goBack();
        }

    }

    private void crearNotaANDback(String title, String body) {
        openActionBarOverflowOrOptionsMenu(mActivityRule.getActivity());
        //onView(withText(R.string.menu_insert)).check(matches(notNullValue()));
        onView(withText(R.string.menu_insert)).perform(click());

        // Se asegura de que la actividad actual es NoteEdit
        onView(withId(R.id.title)).check(matches(notNullValue()));

        // En el título inserta "Espresso Note Title <i>"
        onView(withId(R.id.title)).perform(typeText(title), closeSoftKeyboard());

        // En el cuerpo inserta "Espresso Note Body <i>"
        onView(withId(R.id.body)).perform(typeText(body), closeSoftKeyboard());

        goBack();

    }

    @After
    public void limpiarNotas() {
        listarCatReset();
        listarTodas();
        for(String title : notas) {
            borrarNota(title);
        }
    }

    @After
    public void limpiarCategorias() {
        boolean goBack = false;

        try {
            onView(withId(R.id.title)).check(matches(isDisplayed()));
        } catch (Exception e) {
            onView(withId(R.id.notepad_buttonCat)).perform(click());
            goBack = true;
        }
        for(String title : categorias) {
            borrarCat(title, false);
        }

        if(goBack) {
            goBack();
        }
    }

    private void borrarNota(String title) {
        onView(withText(title)).perform(longClick());
        onView(withText(R.string.menu_delete)).perform(click());
        notas.remove(title);
    }

    private void borrarCat(String title, boolean from_note_list) {
        boolean goBack = false;

        if(from_note_list) {
            try {
                onView(withId(R.id.cat_id)).check(matches(isDisplayed()));
            } catch (Exception e) {
                onView(withId(R.id.notepad_buttonCat)).perform(click());
                goBack = true;
            }
        }
        onView(withText(title)).perform(longClick());
        onView(withText(R.string.menu_cat_delete)).perform(click());
        categorias.remove(title);

        if(goBack) {
            goBack();
        }
    }

    private void listarPorTitulo() {
        onView(withId(R.id.notepad_button)).perform(click());
        onView(withId(R.id.notepad_order)).check(matches(withText("Title")));

    }

    private void listarPorCategoria() {
        onView(withId(R.id.notepad_button)).perform(click());
        onView(withId(R.id.notepad_order)).check(matches(withText("Category")));

    }

    private void listarTodas() {
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("All"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText("All")));
    }

    private void listarVigentes() {
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Planned Notes"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText("Planned Notes")));
    }

    private void listarActivas() {
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Active Notes"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText("Active Notes")));
    }

    private void listarCaducadas() {
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Expired Notes"))).perform(click());
        onView(withId(R.id.spinner)).check(matches(withSpinnerText("Expired Notes")));
    }

    private void goBack() {
        onView(isRoot()).perform(ViewActions.pressBack());
    }



    private void assertNoteEquals(String title, String body, Cursor noteCursor) {
        assertThat(noteCursor.getCount(), is(greaterThanOrEqualTo(1)));
        assertThat(noteCursor.getString(noteCursor.getColumnIndexOrThrow(NotesDbAdapter.KEY_TITLE)),
                is(equalTo(title)));
        assertThat(noteCursor.getString(noteCursor.getColumnIndexOrThrow(NotesDbAdapter.KEY_BODY)),
                is(equalTo(body)));
    }

    private void listarCategorias(String categoria) {
        onView(withId(R.id.notepad_buttonCat)).perform(click());
        onView(withText(categoria)).perform(longClick());
        onView(withText(R.string.menu_cat_select)).perform(click());

        onView(withId(R.id.notepad_category)).check(matches(withText(categoria)));

    }

    private void listarCategoriaAndBack() {
        onView(withId(R.id.notepad_buttonCat)).perform(click());
        goBack();
    }

    private void listarCatReset() {
        onView(withId(R.id.notepad_buttonReset)).perform(click());
    }



    @Test
    public void camino1() {
        crearCategoria(UUID.randomUUID().toString());

        listarPorCategoria();
        listarPorTitulo();
        listarVigentes();
        listarPorCategoria();
        listarActivas();
        listarPorTitulo();
        listarCaducadas();

        listarCategorias(categorias.get(0));

        crearNota(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        listarCatReset();

        listarPorCategoria();
        listarVigentes();
        listarPorTitulo();
        listarActivas();
        listarPorCategoria();
        listarCaducadas();
        listarPorTitulo();

        listarTodas();

        listarCategoriaAndBack();
        listarPorCategoria();

        editarNota(notas.get(0), UUID.randomUUID().toString());
        listarVigentes();
        listarVigentes();
        listarActivas();
        listarVigentes();
        listarCaducadas();
        listarVigentes();
        listarCategorias(categorias.get(0));
        crearCategoria(UUID.randomUUID().toString());

        listarActivas();
        listarActivas();
        listarCaducadas();
        listarActivas();
        listarCategorias(categorias.get(0));

        editarCategoria(categorias.get(1), UUID.randomUUID().toString());
        listarCaducadas();
        listarCaducadas();
        listarCategorias(categorias.get(1));
        borrarCat(categorias.get(1), true);
        listarCategorias(categorias.get(0));

    }

    @Test
    public void camino2() {
        crearNota(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        listarPorCategoria();
        borrarNota(notas.get(0));
        listarPorTitulo();
        listarCatReset();

    }

    @Test
    public void camino3() {
        crearNota(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        listarPorCategoria();
        crearNotaANDback(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        listarPorTitulo();

        editarNota(notas.get(0), UUID.randomUUID().toString());
        listarVigentes();
        crearNotaANDback(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        listarCatReset();
    }

    @Test
    public void camino4() {
        crearNota(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        listarPorCategoria();
        borrarNota(notas.get(0));

        listarPorTitulo();
        listarCatReset();

    }


}
