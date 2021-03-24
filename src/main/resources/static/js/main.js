$(document).ready(function () {
    //Создание и заполнение таблицы юзерами
    loadUsersInformation();
    //Добавление нового юзера
    $('.AddBtn').on('click', function (event) {
        event.preventDefault();
        let user = {
            id: $('#id').val(),
            name: $('#InputName').val(),
            password: $('#InputPassword').val(),
            roles: getRole('#SelectRole')
        };
        console.log(user);
        fetch('api/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(user)
        }).then(result => result.json()
            .then(data => obj = data)
            .then(() => loadUsersInformation()))
            .then(() => openTabById('home'));
        $('input').val('');
        // loadUsersInformation();
    });

    //редактирование юзера
    $('.btnSave').on('click', function (event) {
        event.preventDefault();
        let user = {
            id: $('#id').val(),
            name: $('#name').val(),
            password: $('#password').val(),
            roles: getRole('#SelectEditRole')
        };
        console.log(user);

        fetch('api/update', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(user),
        }).then(function (response) {
            $('input').val('');
            $('.myForm #exampleModal').modal('hide');
            loadUsersInformation();
        })
    });
    //Отображение таблицы авторизованного пользователя
    fetch('api/user').then(result => result.json()
        .then(data => obj = data)
        .then(() => $("#user_auto_table_body").append(createAuTableRow(obj)))).catch(error => {
        console.log(error);
    });

});

function openTabById(tab) {
    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
};

function loadUsersInformation() {

    let UserTableBody = $("#user_table_body");
    UserTableBody.children().remove();
    fetch('api/users')
        .then((response) => {
            response.json().then(data => data.forEach(function (item, i, data) {
                //Создание строки с данными юзера
                let TableRow = createTableRow(item);
                UserTableBody.append(TableRow);

                //Удаление юзера и столбца таблицы
                $('.eBtnDel').on('click', function (event) {
                    event.preventDefault();
                    let href = $(this).attr('href');
                    let id = $(this).attr('id');
                    console.log(id);
                    fetch(href, {method: 'DELETE'})
                        .then(result => console.log(result))
                        .then(() => loadUsersInformation())
                });

                //Заполнение и создание модального окна
                $('.eBtn').on('click', function (event) {
                    event.preventDefault();
                    let href = $(this).attr('href');
                    $.get(href, function (user, status) {
                        $('.myForm #id').val(user.id)
                        $('.myForm #name').val(user.username)
                        $('.myForm #password').val(user.password)

                    });
                    $('.myForm #exampleModal').modal();
                });
            }));
        }).catch(error => {
        console.log(error);
    });
}

//получение ролей для создания юзера
function getRole(address) {
    let data = [];
    $(address).find('option:selected').each(function () {
        data.push({id: $(this).val(), role: $(this).attr('name'), authority: $(this).attr('name')});
    });
    return data;
}

//генерация таблицы с юзерами
function createTableRow(u) {
    // console.log(u);
    const roleUser = u.roles.reduce(
        (sum, {role}) =>
            (sum + role === "ROLE_ADMIN" ? "ADMIN " : "USER "), '');

    return `<tr id="user_table_row_${u.id}">
                <td>${u.id}</td>
                <td>${u.username}</td>
                 <td>${u.password}</td>
                <td>${roleUser}</td>
                <td>
                <a href="/api/${u.id}"  class="btn btn-info eBtn">Edit</a>
                </td>
                <td>
                <a id="${u.id}" href="/api/delete/${u.id}" class="btn btn-danger eBtnDel">Delete</a>
                </td>
            </tr>`;
}

function createAuTableRow(u) {

    const roleUser = u.roles.reduce(
        (sum, {role}) =>
            (sum + role === "ROLE_ADMIN" ? "ADMIN " : "USER "), '');

    return `<tr id="user_table_row_${u.id}">
                <td>${u.id}</td>
                <td>${u.username}</td>
                <td>${u.password}</td>
                <td>${roleUser}</td>
            </tr>`;
}