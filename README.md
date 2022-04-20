FOSSLight Integration
------------------------------------------------

### 도구 설명
- 이 도구는 FossID의 검증 데이터를 엑셀로 출력하여 FOSSLight Hub의 Project에 업로드하기 위한 도구입니다.
- FOSSLight Hub : https://fosslight.org/fosslight/

### 실행 환경
- 이 도구는 OpenJDK 1.8에서 테스트 되었습니다.
- OpenJDK 1.8 다운로드 주소: https://github.com/ojdkbuild/ojdkbuild

### 기능
- FossID의 결과 값을 두가지 방법으로 확인하여 엑셀에 저장합니다.
  + '--fididresult 0': FossID에서 검증된 파일 중 'Marked as Identified'로 상태 값이 변경된 파일들만 순회합니다. 각 파일별 Commment가 2개 이상 입력 되더라도 엑셀 및 FOSSLight에 모든 Comment의 이관이 가능합니다.
  + '--fididresult 1': FossID에서 검증된 파일 중 상태 값이 'Marked as Identified'로 변경된 파일 뿐만 아니라 상태 값이 'Pending Identification'로 된 파일 중 하나 이상의 컴포넌트가 Assign된 파일들을 순회합니다. 각 파일별 Comment가 2개 이상 입력 되더라도 엑셀 및 FOSSLight에는 하나의 Comment만 이관이 가능합니다.
- FossID 레포트가 저장되는 경로는 FOSSLight Integration 도구가 실행되는 경로에 저장되거나 사용자가 '--filepath' 및 'fossid.filepath'를 지정하여 레포트가 저장되는 경로를 변경할 수 있습니다.
- FOSSLight에 프로젝트/버전 생성 시 다음과 같은 순서로 사용됩니다.
  + 먼저, '--fltprojectname' 및 '--fltprojectversion'이 사용됩니다.
  + 다음, config.properties 파일의 'fosslight.project' 및 'fosslight.version' 값이 사용됩니다.
  + 사용자가 지정하지 않을 경우 FossID의 ProjectName 및 ScanName을 사용합니다.

# 사용 방법

### 실행 방법(Terminal)
- 파라미터는 config.properties 파일에 정의된 값 보다 우선 사용됩니다.
```bash
$ java -jar class [args....]

e.g)
$ java -jar fosslight_integration.jar --fidprotocol https --fidaddress fossid.osbc.co.kr --fidusername byunghoon --fidapikey fidapikey --fidprojectname projectName --fidscanname scanName --fltprotocol https --fltaddress fosslight.osbc.co.kr --flttoken flttoken
```

#### 파라미터
```
FossID Server Information
--fidprotocol: FossID web interface protocol
--fidaddress: FossID address
--fidusername: FossID username
--fidapikey: FossID apikey


FossID Project/Scan Information
--fidprojectname: FossID project name
--fidscanname: FossID scan name


FossID Report Option 
--fididresult: Choice a identification result, 0: Only marked as identified files, 1: Marked as Identified files & Pending files with assigned components
               (default: 0, 1)
--fidcopyright: Choice a copyright, 0: Identified by Shinobi, 1: Component copyright
                (default: 0, 1)
--filepath: Set FossID report file path, /some/path/to/be/saved
            (default: Saved in FOSSLight integration tool path)
--filedelete: Delete the FossID report file after importing the FossID report file into FOSSLight
              (0, default: 1)


FOSSLight Server Information
--fltprotocol: FOSSLight web interface protocol  
--fltaddress: FOSSLight address
--flttoken: FOSSLight token


FOSSLight Project/Scan Information
--fltprojectname: FOSSLight project name
                  (default: fossidProjectName)
--fltprojectversion: FOSSLight project version
                  (default: fossidScanName)
--fltostype: FOSSLight project OS type
             (defulat: Linux, Windows, MacOS, ETC)
```

### 실행 방법(IntelliJ)
parameter를 입력하는 것 대신 config.properties 파일을 수정하여 추출이 필요한 내용을 작성할 수 있습니다. config.properties를 수정하면 5번부터 다시 진행합니다.
1. 저장소를 Clone 한 뒤, [IntelliJ](https://www.jetbrains.com/idea/download) 에서 Open project로 프로젝트를 엽니다.
2. `src/main/resources/config.properties` 파일에 내용을 채워넣습니다. 
3. IntelliJ > File > Project Structure > Project Settings > Artifacts
4. `+` 버튼 클릭 > Jar > From modules with dependencies > 폴더모양 아이콘 클릭 > Main Class 선택(`integration.main.main`) > OK
5. IntelliJ > Build > Build Artifacts 를 클릭하면 out이라는 폴더에 JAR 파일이 생성됩니다.
6. 생성된 JAR 파일에서 우클릭 > Run

