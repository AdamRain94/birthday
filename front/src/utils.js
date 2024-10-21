import api from '@/axios.js';
import default_photo from '@/assets/images/default_photo_user.png';


async function loadUserPhoto(user) {
    if (!user.photo) {
        return default_photo;
    }
    try {
        const response = await api.get(`/files/photo/user/${user.photo}`, { responseType: 'blob' });
        return URL.createObjectURL(response.data);
    } catch (error) {
        return default_photo;
    }
}

function filter(value, setError) {
    const regex = /^[А-Яа-яЁё\s]*$/;
    setError('');
    if (!regex.test(value)) {
        setError('Разрешены только русские буквы!');
        value = value.replace(/[^А-Яа-яЁё\s]/g, '');
    }
    return value;
}

function filterTel(value, setError) {
    const regex = /^[0-9()\s+-]*$/;
    setError('');
    if (!regex.test(value)) {
        setError('Разрешены только цифры и специальные символы!');
        value = value.replace(/[^0-9()\s+-]/g, '');
    }
    return value;
}

export {loadUserPhoto, filter, filterTel};