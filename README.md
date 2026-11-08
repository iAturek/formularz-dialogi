# formularz-dialogi

## Zadanie 1
Różnica wynika z liczby metod w interfejsach obsługujących te komponenty:

* **CheckBox** korzysta z interfejsu funkcjonalnego, który ma tylko 1 metodę (`onCheckedChanged`).
* **SeekBar** korzysta z interfejsu, który ma aż 3 metody (`onProgressChanged`, `onStartTrackingTouch`, `onStopTrackingTouch`). Lambda nie potrafi obsłużyć wielu metod jednocześnie.

## Zadanie 2
Które interfejsy? SeekBar.OnSeekBarChangeListener (ma 3 metody) oraz AdapterView.OnItemSelectedListener od Spinnera (ma 2 metody).
Co z tego wynika?
* Brak lambd: Lambda działa tylko wtedy, gdy interfejs ma dokładnie 1 metodę (np. przy przycisku lub CheckBoxie).
* Więcej kodu: Musisz użyć tradycyjnej klasy anonimowej i napisać puste, niepotrzebne metody (np. onStartTrackingTouch), tylko po to, aby kompilator nie zgłaszał błędu.

