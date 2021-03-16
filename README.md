# FlipTimer

일하는 시간과 쉬는 시간을 설정하면, 번갈아가며 타이머가 작동합니다.

---
- 시간을 설정할 때, 버튼을 누르거나 숫자를 직접 입력하게 되면 번거롭기도 하고 UI도 깔끔하지 못할거라고 생각했습니다.
그래서 TextView를 상속한 커스텀 뷰를 만들어서 onTouchEvent에서 스와이프하면 원하는 시간을 설정할 수 있게 만들었습니다.

- 쉬는 시간에서 일하는 시간으로 넘어가는 트랜지션을 만들때, ConstraintSet을 이용해서 각각의 뷰가 움직일 수 있게 했습니다.

- 일체감 있는 UI를 위해서 상태바 부분을 같은 색으로 설정했습니다.

+ 플레이스토어 : https://play.google.com/store/apps/details?id=com.cnux9.fliptimer
