import vue from '../main';

export default {
    success(response, msg, block) {
        vue.$notify({
            group: 'noti',
            type: 'success',
            text: msg,
        });
        block();
    },

    error(error, msg, block) {
            vue.$notify({
                group: 'noti',
                type: 'error',
                title: error.response.state,
                text: error.response.data.message,
            });
            block();
    },

    warn(msg) {
        vue.$notify({
            group: 'noti',
            type: 'warning',
            text: msg,
        });
    },
};
