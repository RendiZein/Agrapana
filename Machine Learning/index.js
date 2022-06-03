// NOTE: untuk deploy di API menyesuaikan dari sini
// di Node js perlu install tfjs: npm install @tensorflow/tfjs-node, import * as tf from '@tensorflow/tfjs'
// TODO:
// - Inisialisasi model (di awal biar performanya lebih cepet, ga harus load model setiap request)
// - Terima request post image di endpoint API
// - Load image
// - Predict, nanti return json
// Belom nyoba di node jsnya tapi kira-kira mekanismenya seperti itu ... ^_^

const MODEL_PATH = 'banana_ripeness_tfjs/model.json';
const IMAGE_SIZE = 150;
// NOTE: class labels masih bisa disesuaikan
const CLASS_LABELS = {
    0: 'green',
    1: 'overripe',
    2: 'ripe'
};
// NOTE: message masih bisa disesuaikan
const message = {
    0: 'Pisang masih belum matang ...',
    1: 'Pisang ini terlalu matang ...',
    2: 'Pisang ini kematangannya pas \(^_^)/'
}
var model;

// NOTE: hanya untuk keperluan di html
const selected_image = document.getElementById('selected-image');
const image_selector = document.getElementById('image-selector');
const predict_result = document.getElementById('prediction-result');

async function initialize() {
    model = await tf.loadGraphModel(MODEL_PATH);
}

initialize();

async function predict(image) {
    predict_result.innerText = 'Predicting ...';

    const startTime = performance.now();
    const res = tf.tidy(() => {
        var img = tf.browser.fromPixels(image).toFloat();
        // Resize image
        img = img.reshape([1, IMAGE_SIZE, IMAGE_SIZE, 3]);
        // Normalize image
        const offset = tf.scalar(255);
        img = img.div(offset);
        return model.predict(img);
    });

    var prediction = await res.data();

    const class_idx = res.argMax(1).dataSync();
    
    // Total time kalo mau dipake
    const totalTime = performance.now() - startTime;

    // Return object sebagai response API
    objReturn = {
        class: CLASS_LABELS[class_idx],
        confident: prediction[class_idx],
        detail: message[class_idx]
    };

    predict_result.innerText = JSON.stringify(objReturn);
}

image_selector.addEventListener('change', e => {
    let files = e.target.files;

    let reader = new FileReader();
    reader.onload = er => {
        // Load image
        let img = new Image();
        img.src = er.target.result;
        img.width = IMAGE_SIZE;
        img.height = IMAGE_SIZE;
        
        // Tampilin image di html
        selected_image.src = er.target.result;
        selected_image.width = IMAGE_SIZE;
        selected_image.height = IMAGE_SIZE;

        // Predict
        img.onload = () => predict(img);
    };

    reader.readAsDataURL(files[0]);
});
