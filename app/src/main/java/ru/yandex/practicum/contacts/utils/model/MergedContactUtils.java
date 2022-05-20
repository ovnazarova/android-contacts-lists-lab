package ru.yandex.practicum.contacts.utils.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import ru.yandex.practicum.contacts.model.ContactType;
import ru.yandex.practicum.contacts.model.MergedContact;

public class MergedContactUtils {

    public static boolean contains(MergedContact contact, String query) {
        if (TextUtils.isEmpty(query)) {
            return true;
        }
        return contact.getFirstName().contains(query) ||
                contact.getMiddleName().contains(query) ||
                contact.getSurname().contains(query) ||
                contact.getNormalizedNumber().contains(query) ||
                contact.getPhone().contains(query) ||
                contact.getEmail().contains(query);
    }

    public static boolean contains(MergedContact contact, Set<ContactType> types) {
        if (types.isEmpty()){
            return true;
        }
        final List<ContactType> contactTypes = getContactTypes(contact);
        return !Collections.disjoint(contactTypes, types);
    }

    public static List<ContactType> getContactTypes(MergedContact contact) {
        final List<ContactType> allTypes = contact.getContactTypes().stream()
                .map(ContactTypeUtils::parse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (!TextUtils.isEmpty(contact.getPhone())) {
            allTypes.add(ContactType.PHONE);
        }
        if (!TextUtils.isEmpty(contact.getEmail())) {
            allTypes.add(ContactType.EMAIL);
        }
        Collections.sort(allTypes);

        return Collections.unmodifiableList(allTypes);
    }
}
